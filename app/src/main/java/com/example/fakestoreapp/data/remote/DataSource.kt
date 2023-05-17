package com.example.fakestoreapp.data.remote

import io.reactivex.rxjava3.core.Single

interface DataSource {
    fun makeGetRequest(path: String,params:HashMap<String?, Any?>,token:String? = null): Single<String>?
}