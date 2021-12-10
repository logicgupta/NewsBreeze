package com.logic.newsbreeze.savedList

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.logic.newsbreeze.R
import com.logic.newsbreeze.databinding.ActivitySavedNewsBinding
import com.logic.newsbreeze.main.model.ArticlesItem
import com.logic.newsbreeze.savedList.adapter.ItemListAdapter

class SavedNewsActivity : Activity() {

    companion object {

        private const val INTENT_EXTRA = "DATA"

        fun generateBundle(res: ArrayList<ArticlesItem>): Bundle {
            return Bundle().apply {
                putParcelableArrayList(INTENT_EXTRA, res)
            }
        }
    }

    lateinit var itemListAdapter: ItemListAdapter
    private lateinit var binding: ActivitySavedNewsBinding
    var list = ArrayList<ArticlesItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras!!.getParcelableArrayList<ArticlesItem>(INTENT_EXTRA)!!.isNullOrEmpty()) {
            hideView()
        } else {
            list.addAll(intent.extras!!.getParcelableArrayList(INTENT_EXTRA)!!)
            binding.rvList.apply {
                layoutManager = LinearLayoutManager(this@SavedNewsActivity)
            }
            itemListAdapter = ItemListAdapter(this)
            binding.rvList.adapter = itemListAdapter
            itemListAdapter.addData(list)
            showView()
        }
        binding.imvBack.setOnClickListener {
            finish()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    filter(newText)
                } else {
                    itemListAdapter.addData(list)
                }
                return true
            }

        })
        binding.searchView.setOnCloseListener {
            binding.searchView.clearFocus()
            itemListAdapter.addData(list)
            false
        }
    }

    private fun showView() {
        binding.searchView.visibility = View.VISIBLE
        binding.textView.visibility = View.VISIBLE
        binding.rvList.visibility = View.VISIBLE
        binding.tvSeeAll.visibility = View.GONE
        binding.loader.errorLayout.visibility = View.GONE

    }

    private fun hideView() {
        binding.searchView.visibility = View.GONE
        binding.textView.visibility = View.GONE
        binding.rvList.visibility = View.GONE
        binding.tvSeeAll.visibility = View.GONE
        binding.loader.loader.visibility = View.GONE
        binding.loader.errorMessage.text = getString(R.string.no_article_saved)
    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filterNames: ArrayList<ArticlesItem> = ArrayList()

        //looping through existing elements
        for (s in list) {
            //if the existing elements contains the search input
            if (s.title!!.contains(text, false)) {
                //adding the element to filtered list
                filterNames.add(s)
            }
        }
        //calling a method of the adapter class and passing the filtered list
        if (filterNames.size == 0) {
            binding.loader.errorLayout.visibility = View.VISIBLE
            binding.loader.loader.visibility = View.GONE
            binding.loader.errorMessage.text = getString(R.string.no_results)
            binding.rvList.visibility = View.GONE
        } else {
            showView()
            itemListAdapter.addData(filterNames)
        }

    }

}