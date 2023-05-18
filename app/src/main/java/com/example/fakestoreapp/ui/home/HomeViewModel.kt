package com.example.fakestoreapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestoreapp.data.models.Product
import com.example.fakestoreapp.usecases.GetAllCategoriesUseCase
import com.example.fakestoreapp.usecases.GetProductsUseCase
import com.example.fakestoreapp.usecases.SavedProductsUseCases
import com.example.fakestoreapp.utils.NetworkUtils.getNetworkError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val savedProductsUseCases: SavedProductsUseCases
    )
: ViewModel() {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var errMessage: MutableLiveData<String> = MutableLiveData()
    var products: MutableLiveData<List<Product>> = MutableLiveData()
    var savedProducts: MutableLiveData<List<Product>> = MutableLiveData()
    var dataSetUp: MutableLiveData<Int> = MutableLiveData(0)
    var categories: MutableLiveData<List<String>> = MutableLiveData()
    var sortStrategy: MutableLiveData<String> = MutableLiveData()
    var limitProducts: MutableLiveData<Int> = MutableLiveData()

    fun getProducts(category: String? = null){
        isLoading.value = true
        getProductsUseCase.getAllProducts(category, limitProducts.value, sortStrategy.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                isLoading.postValue(false)
                products.value = response
                dataSetUp.postValue(dataSetUp.value?.plus(1) ?: 0)
            }, { throwable ->
                isLoading.postValue(false)
                errMessage.value = throwable.getNetworkError()
            })
    }
    fun getAllCategories() {
        isLoading.value = true
        getAllCategoriesUseCase.getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                isLoading.postValue(false)
                categories.value = response
            }, { throwable ->
                isLoading.postValue(false)
                errMessage.value = throwable.getNetworkError()
            })
    }

    fun getSavedProducts() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            savedProducts.postValue(savedProductsUseCases.getAllSavedProducts())
            dataSetUp.postValue(dataSetUp.value?.plus(1) ?: 0)
            isLoading.postValue(false)
        }
    }
    fun addProductToSavedProducts(product: Product) {
        isLoading.value = true
        val newList = savedProducts.value?.toMutableList()
        newList?.add(product)
        savedProducts.postValue(newList?.toList())
        viewModelScope.launch(Dispatchers.IO){
            savedProductsUseCases.addProductToSavedProducts(product)
            isLoading.postValue(false)
        }
    }
    fun deleteProductFromSavedProducts(product: Product) {
        isLoading.value = true
        val newList = savedProducts.value?.toMutableList()
        newList?.remove(product)
        savedProducts.postValue(newList?.toList())
        viewModelScope.launch(Dispatchers.IO){
            savedProductsUseCases.deletedProductFromSavedProducts(product)
            isLoading.postValue(false)
        }
    }
}