package com.example.fakestoreapp.data.repositories

import com.example.fakestoreapp.data.remote.NetworkManager
import com.example.fakestoreapp.data.remote.responses.GetAllCategoriesResponse
import com.example.fakestoreapp.data.remote.responses.GetProductsResponse
import com.example.fakestoreapp.utils.JsonConverter
import com.example.fakestoreapp.utils.NetworkConstants
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductsRepository(private val networkManager: NetworkManager) {
    fun getAllProducts(): Single<GetProductsResponse>{
        return Single.create { singleEmitter ->
            val requestHashMap: HashMap<String?, Any?> = hashMapOf()
            networkManager.makeGetRequest(
                NetworkConstants.allProductsApi,
                requestHashMap
            )?.subscribeOn(Schedulers.io())
                ?.observeOn(Schedulers.io())
                ?.subscribe({
                    val newJson = "{\"products\":$it}"
                    JsonConverter.convertJsonToObj(newJson, GetProductsResponse::class.java)?.let {
                        singleEmitter.onSuccess(it)
                    }
                }, {
                    singleEmitter.onError(it)
                })
        }
    }
}