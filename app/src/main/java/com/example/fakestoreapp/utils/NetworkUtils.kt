package com.example.fakestoreapp.utils

import com.example.fakestoreapp.data.remote.responses.ErrorResponse
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetworkUtils {
    fun Throwable.getNetworkError(): String {
        return when {
            this is UnknownHostException || this is SocketTimeoutException -> "No Internet Connection"
            this is HttpException && this.code() == 500 -> "Server Error"
            this is HttpException && this.code() == 0 -> this.cause.toString()
            this is HttpException  -> {
                this.response()?.errorBody()?.string()?.let {
                    JsonConverter.convertJsonToObj(it, ErrorResponse::class.java)?.message?:
                    JsonConverter.convertJsonToObj(it, ErrorResponse::class.java)?.detail?:""

                }  ?: "Unexpected error"
            }
            else -> this.message ?: "Unexpected error"
        }
    }

}