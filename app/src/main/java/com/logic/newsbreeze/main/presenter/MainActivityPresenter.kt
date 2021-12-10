package com.logic.newsbreeze.main.presenter

import com.logic.newsbreeze.main.model.ArticlesItem
import com.logic.newsbreeze.main.model.Response

interface MainActivityPresenter {
    fun getNews()
    interface View {
        fun onSuccess(response: Response)
        fun onFailure(errorMsg: String)

    }
}