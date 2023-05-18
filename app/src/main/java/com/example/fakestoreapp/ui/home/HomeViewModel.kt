package com.example.fakestoreapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fakestoreapp.data.models.Product
import com.example.fakestoreapp.usecases.GetAllCategoriesUseCase
import com.example.fakestoreapp.usecases.GetProductsUseCase
import com.example.fakestoreapp.utils.NetworkUtils.getNetworkError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
    )
: ViewModel() {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var errMessage: MutableLiveData<String> = MutableLiveData()
    var products: MutableLiveData<List<Product>> = MutableLiveData()
    var categories: MutableLiveData<List<String>> = MutableLiveData()

    fun getProducts(category: String? = null){
        isLoading.value = true
        getProductsUseCase.getProducts(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                isLoading.value = false
                products.value = response
            }, { throwable ->
                isLoading.value = false
                errMessage.value = throwable.getNetworkError()
            })
    }
    fun getAllCategories(){
        isLoading.value = true
        getAllCategoriesUseCase.getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                isLoading.value = false
                categories.value = response
            }, { throwable ->
                isLoading.value = false
                errMessage.value = throwable.getNetworkError()
            })
    }
}