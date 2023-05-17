package com.example.fakestoreapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fakestoreapp.data.models.Product
import com.example.fakestoreapp.usecases.GetAllProductsUseCase
import com.example.fakestoreapp.utils.NetworkUtils.getNetworkError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getAllProductsUseCase: GetAllProductsUseCase)
: ViewModel() {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var errMessage: MutableLiveData<String> = MutableLiveData()
    var products: MutableLiveData<List<Product>> = MutableLiveData()
    init {
        getAllProducts()
    }
    private fun getAllProducts(){
        getAllProductsUseCase.getAllProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                isLoading.value = false
                products.value = response.products
            }, { throwable ->
                isLoading.value = false
                errMessage.value = throwable.getNetworkError()
            })
    }
}