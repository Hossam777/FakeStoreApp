package com.example.fakestoreapp.data.remote

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class NetworkManager constructor(private val apiService: ApiService) : DataSource {

    override fun makeGetRequest(path: String,params: HashMap<String?, Any?>,token: String?, ): Single<String>? {
        return Single.create { singleEmitter ->
            apiService.getApi(path,token,params)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    singleEmitter.onSuccess(it.string())
                }, {
                    singleEmitter.onError(it)
                })
        }
    }
}