package com.logic.newsbreeze.api

import ApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    fun apiInterface(): ApiInterface {
        val retrofitBuilder = getRetrofitBuilder
        retrofitBuilder.client(getOkHttpClientBuilder.build())
        return retrofitBuilder.build().create(ApiInterface::class.java)
    }

    private val getOkHttpClientBuilder: OkHttpClient.Builder by lazy {
        val builder = OkHttpClient.Builder()
        if (ApiInterface.TIMEOUT_SECONDS > 0) {
            builder.connectTimeout(ApiInterface.TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }
        builder
    }

    private val getRetrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(ApiInterface.CONVERTER_FACTORY)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }
}

