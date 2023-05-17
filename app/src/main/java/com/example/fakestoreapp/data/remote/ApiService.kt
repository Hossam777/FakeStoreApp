package com.example.fakestoreapp.data.remote

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    @GET("{path}")
    fun getApi(@Path(value = "path",encoded = true) path: String,
               @Header("Authorization") authToken:String? = null
               ,@QueryMap params: HashMap<String?, Any?>?): Single<ResponseBody>
}