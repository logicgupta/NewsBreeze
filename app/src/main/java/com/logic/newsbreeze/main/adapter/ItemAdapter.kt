package com.logic.newsbreeze.main.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.logic.newsbreeze.R
import com.logic.newsbreeze.loadImage
import com.logic.newsbreeze.main.model.ArticlesItem
import com.logic.newsbreeze.monthFromDate
import java.util.*

class ItemAdapter(private val callBack: CallBack, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val backDrawable: Drawable =
        ContextCompat.getDrawable(context, R.drawable.saved_design)!!
    private val savedDrawable: Drawable =
        ContextCompat.getDrawable(context, R.drawable.img_bookmark)!!

    private val backUnsavedDrawable: Drawable =
        ContextCompat.getDrawable(context, R.drawable.unsaved_design)!!
    private val unSavedDrawable: Drawable =
        ContextCompat.getDrawable(context, R.drawable.img_saved)!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val data = articleList[position]
            if (!data.urlToImage.isNullOrEmpty()) {
                holder.coverImage.loadImage(data.urlToImage)
            }
            holder.title.text = data.title
            holder.desc.text = data.description
            holder.time.text = monthFromDate(data.publishedAt!!)

            if (data.isSaved) {
                holder.saveIcon.background = backDrawable
                holder.saveIcon.setImageDrawable(savedDrawable)
                holder.saveBtn.visibility = View.GONE

            } else {
                holder.saveIcon.background = backUnsavedDrawable
                holder.saveIcon.setImageDrawable(unSavedDrawable)
                holder.saveBtn.visibility = View.VISIBLE
            }

            holder.saveBtn.setOnClickListener {
                if (!data.isSaved) {
                    callBack.saveData(position, data)
                }
            }

            holder.readBtn.setOnClickListener {
                callBack.onClickItem(holder.layoutPosition, articleList[holder.layoutPosition])
            }
        }
    }


    private val articleList = ArrayList<ArticlesItem>();

    override fun getItemCount(): Int = articleList.size

    fun addData(list: List<ArticlesItem>) {
        articleList.clear()
        articleList.addAll(list)
        notifyDataSetChanged()
    }

    fun updateToSave(position: Int, articlesItem: ArticlesItem) {
        articleList[position] = articlesItem
        notifyItemChanged(position)
    }


    inner class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val coverImage: ImageView = view.findViewById(R.id.imv_cover)
        val saveIcon: ImageView = view.findViewById(R.id.imv_save_btn)
        val title: TextView = view.findViewById(R.id.tv_title)
        val desc: TextView = view.findViewById(R.id.tv_desc)
        var time: TextView = view.findViewById(R.id.tv_date)
        val readBtn: TextView = view.findViewById(R.id.btn_read)
        val saveBtn: TextView = view.findViewById(R.id.btn_save)
    }

    interface CallBack {
        fun onClickItem(position: Int, item: ArticlesItem)
        fun saveData(position: Int, item: ArticlesItem)
    }


}