package com.logic.newsbreeze.main.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.logic.newsbreeze.R
import com.logic.newsbreeze.databinding.ActivityMainBinding
import com.logic.newsbreeze.detail.DetailActivity
import com.logic.newsbreeze.launchActivity
import com.logic.newsbreeze.launchActivityForResult
import com.logic.newsbreeze.main.adapter.ItemAdapter
import com.logic.newsbreeze.main.model.ArticlesItem
import com.logic.newsbreeze.main.model.Response
import com.logic.newsbreeze.main.presenter.MainActivityPresenter
import com.logic.newsbreeze.main.presenter.MainActivityPresenterImpl
import com.logic.newsbreeze.savedList.SavedNewsActivity
import com.logic.newsbreeze.showToast
import com.logic.newsbreeze.utils.AppConstants

class MainActivity : Activity(), MainActivityPresenter.View, ItemAdapter.CallBack {

    private lateinit var presenter: MainActivityPresenterImpl
    lateinit var adapter: ItemAdapter
    private lateinit var binding: ActivityMainBinding
    var list = ArrayList<ArticlesItem>()
    private var userActionIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainActivityPresenterImpl(this, this)
        presenter.getNews()

        adapter = ItemAdapter(this, this)

        val horizontalLayout = LinearLayoutManager(this)
        binding.rvList.layoutManager = horizontalLayout

        binding.imvSavedBtn.setOnClickListener {
            launchActivity(
                SavedNewsActivity::class.java,
                SavedNewsActivity.generateBundle(getSavedData())
            )
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    filter(newText)
                } else {
                    adapter.addData(list)
                }
                return true
            }

        })
        binding.searchView.setOnCloseListener {
            binding.searchView.clearFocus()
            adapter.addData(list)
            false
        }
    }

    override fun onSuccess(response: Response) {
        showView()
        list.clear()
        list.addAll(response.articles!!)
        adapter.addData(response.articles)
        binding.rvList.adapter = adapter
    }

    override fun onFailure(errorMsg: String) {
        hideView(errorMsg)
    }

    override fun onClickItem(position: Int, item: ArticlesItem) {
        userActionIndex = position
        launchActivityForResult(
            DetailActivity::class.java,
            AppConstants.INTENT_ACTION_IS_SAVED,
            DetailActivity.generateBundle(item)
        )
    }

    override fun saveData(position: Int, item: ArticlesItem) {
        item.isSaved = true
        list[position].isSaved = true
        adapter.updateToSave(position, item)
        showToast("Article Saved Successfully !")
    }

    private fun getSavedData(): ArrayList<ArticlesItem> {
        val savedList = ArrayList<ArticlesItem>()
        list.forEach { item ->
            if (item.isSaved) {
                savedList.add(item)
            }
        }
        return savedList
    }

    private fun showView() {
        binding.loader.errorLayout.visibility = View.GONE
        binding.searchView.visibility = View.VISIBLE
        binding.rvList.visibility = View.VISIBLE
    }

    private fun hideView(errorMsg: String) {
        binding.loader.loader.visibility = View.GONE
        binding.loader.errorMessage.text = errorMsg
        binding.searchView.visibility = View.GONE
        binding.rvList.visibility = View.GONE
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
            adapter.addData(filterNames)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                AppConstants.INTENT_ACTION_IS_SAVED -> {
                    if (data!!.extras!!.getBoolean("isSaved")) {
                        val item: ArticlesItem = data.extras!!.getParcelable("data")!!
                        list[userActionIndex].isSaved = true
                        adapter.updateToSave(userActionIndex, item)
                    }
                }
            }
        }
    }
}