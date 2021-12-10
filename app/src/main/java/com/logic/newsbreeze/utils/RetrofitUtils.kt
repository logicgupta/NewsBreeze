package com.logic.newsbreeze.utils

import android.content.Context
import com.logic.newsbreeze.R
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


object RetrofitUtils {
    /**
     * Checks if the response is successful. Additionally, logs the error if response is not successful.
     *
     * @param response The response to check
     * @return true if response is successful. Otherwise, return false after calling
     * [.logFailedResponse].
     */
    @JvmStatic
    fun isResponseSuccessful(response: Response<*>): Boolean {
        if (response.isSuccessful) {
            return true
        }
        logFailedResponse(response)
        return false
    }

    /** Log the response error in LogCat as well as with Crashlytics. */
    @JvmStatic
    fun logFailedResponse(response: Response<*>) {
        val throwable: Throwable = try {
            Throwable(
                "Response is not successful. HTTP status code: " + response.code() + ". Raw response: " +
                        response.errorBody()!!.string()
            )
        } catch (e: IOException) {
            Throwable("Response is not successful. HTTP status code: " + response.code())
        }
    }

    fun showMessage(context: Context, e: Throwable): String? {
        return if (e is UnknownHostException) {
            context.getString(R.string.app_error_check_internet)
        } else if (!e.message.isNullOrEmpty()) {
            e.message
        } else {
            context.getString(R.string.unknown_error_message)
        }
    }

    fun isUnAuthorized(throwable: Throwable): Boolean {
        return if (throwable is UnknownHostException || throwable is SocketTimeoutException) {
            false
        } else {
            (throwable as HttpException?)!!.code() == 401
        }

    }

    @JvmStatic
    fun getErrorMessage(context: Context, throwable: Throwable): String? {
        if (throwable is UnknownHostException || throwable is SocketTimeoutException) {
            return context.getString(R.string.app_error_check_internet)
        } else if (throwable is HttpException) {
            try {
                return context.getString(R.string.unknown_error_message)
            } catch (e: Exception) {
                return throwable.message
            }
        }
    return context.getString(R.string.unknown_error_message)
}

}