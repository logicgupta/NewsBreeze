package com.logic.newsbreeze.main.presenter

import android.content.Context
import android.util.Log
import com.logic.newsbreeze.R
import com.logic.newsbreeze.api.RetrofitClient
import com.logic.newsbreeze.main.model.Response
import com.logic.newsbreeze.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainActivityPresenterImpl(val mContext: Context, val view: MainActivityPresenter.View) :
    MainActivityPresenter {
    override fun getNews() {
        RetrofitClient.apiInterface().getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Response>() {
                override fun onSuccess(t: Response) {
                    if (t.status == "ok") {
                        view.onSuccess(t)
                    } else {
                        view.onFailure(mContext.getString(R.string.unknown_error_message))
                    }
                }

                override fun onError(e: Throwable) {
                    Log.e("Error", "" + e)
                    RetrofitUtils.getErrorMessage(mContext, e).apply {
                        view.onFailure(toString())
                    }
                }

            })
    }


}