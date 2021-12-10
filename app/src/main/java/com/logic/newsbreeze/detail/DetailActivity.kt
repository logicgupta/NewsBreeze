package com.logic.newsbreeze.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.logic.newsbreeze.R
import com.logic.newsbreeze.databinding.ActivityDetailBinding
import com.logic.newsbreeze.loadImage
import com.logic.newsbreeze.main.model.ArticlesItem
import com.logic.newsbreeze.monthFromDate
import com.logic.newsbreeze.showToast

class DetailActivity : Activity() {

    companion object {

        private const val INTENT_EXTRA = "DATA"

        fun generateBundle(res: ArticlesItem): Bundle {
            return Bundle().apply {
                putParcelable(INTENT_EXTRA, res)
            }
        }
    }

    private var data = ArticlesItem()
    private var isSaved: Boolean = false
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras!!.getParcelable<ArticlesItem>(INTENT_EXTRA) != null) {
            data = intent.extras!!.getParcelable(INTENT_EXTRA)!!

            if (data.isSaved) {
                binding.btnSave.visibility = View.GONE
                binding.imvSaved.background =
                    ContextCompat.getDrawable(this, R.drawable.saved_design)!!
                binding.imvSaved.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.img_bookmark
                    )!!
                )
            }

            binding.imvCover.loadImage(data.urlToImage)
            binding.tvDate.text = monthFromDate(data.publishedAt!!)
            binding.tvTitle.text = data.title
            binding.tvAuthName.text = data.author
            binding.tvUsername.text = "@${data.author!!.replace(" ", "_")}"
            binding.tvDesc.text = data.description
            binding.tvAuthName.text = data.author

            binding.btnSave.setOnClickListener {
                binding.imvSaved.background =
                    ContextCompat.getDrawable(this, R.drawable.saved_design)!!
                binding.imvSaved.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.img_bookmark
                    )!!
                )
                showToast("Article Saved Successfully !")
                binding.btnSave.visibility = View.GONE
                data.isSaved = true
                isSaved = true
            }
        }

        binding.imvBack.setOnClickListener {
            super.onBackPressed()
            setIntent()
        }

    }

    override fun onBackPressed() {
        setIntent()
    }

    private fun setIntent() {
        val intent = Intent()
        if (data.isSaved) {
            intent.putExtra("isSaved", true)
            intent.putExtra("data", data)

        } else {
            intent.putExtra("isSaved", false)
            intent.putExtra("data", data)

        }
        setResult(RESULT_OK, intent)
        finish()

    }
}