package com.example.fakestoreapp.usecases

import com.example.fakestoreapp.data.models.Product
import com.example.fakestoreapp.data.repositories.ProductsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetProductsUseCase@Inject constructor(private val productsRepository: ProductsRepository) {
    fun getProducts(category: String?): Single<List<Product>> {
        return productsRepository.getAllProducts(category)
    }
}