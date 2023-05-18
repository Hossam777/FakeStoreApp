package com.example.fakestoreapp.data.repositories

import com.example.fakestoreapp.data.local.SavedProductsDao
import com.example.fakestoreapp.data.models.Product
import com.example.fakestoreapp.data.remote.NetworkManager
import com.example.fakestoreapp.utils.JsonConverter
import com.example.fakestoreapp.utils.NetworkConstants
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductsRepository(
    private val networkManager: NetworkManager
    , private val savedProductsDao: SavedProductsDao
) {
    fun getAllProducts(category: String?, limit: Int?, sort: String?): Single<List<Product>>{
        return Single.create { singleEmitter ->
            val requestHashMap: HashMap<String?, Any?> = hashMapOf()
            var url = NetworkConstants.allProductsApi
            category?.let { url += "category/${category}" }
            limit?.let { requestHashMap["limit"] = limit }
            sort?.let { requestHashMap["sort"] = sort }
            networkManager.makeGetRequest(
                url,
                requestHashMap
            )?.subscribeOn(Schedulers.io())
                ?.observeOn(Schedulers.io())
                ?.subscribe({
                    JsonConverter.convertJsonToArray(it, Product::class.java)?.let {
                        singleEmitter.onSuccess(it)
                    }
                }, {
                    singleEmitter.onError(it)
                })
        }
    }
    fun getAllCategories(): Single<List<String>>{
        return Single.create { singleEmitter ->
            val requestHashMap: HashMap<String?, Any?> = hashMapOf()
            networkManager.makeGetRequest(
                NetworkConstants.allCategoriesApi,
                requestHashMap
            )?.subscribeOn(Schedulers.io())
                ?.observeOn(Schedulers.io())
                ?.subscribe({
                    JsonConverter.convertJsonToArray(it, String::class.java)?.let {
                        singleEmitter.onSuccess(it)
                    }
                }, {
                    singleEmitter.onError(it)
                })
        }
    }
    suspend fun getAllSavedProducts(): List<Product>?{
        return savedProductsDao.getSavedProducts()
    }
    suspend fun addProductToSavedProducts(product: Product){
        savedProductsDao.addProduct(product)
    }
    suspend fun removeProductFromSavedProducts(id: Int){
        savedProductsDao.delete(id)
    }
}