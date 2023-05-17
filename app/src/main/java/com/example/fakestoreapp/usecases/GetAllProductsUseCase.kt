package com.example.fakestoreapp.usecases

import com.example.fakestoreapp.data.remote.responses.GetProductsResponse
import com.example.fakestoreapp.data.repositories.ProductsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAllProductsUseCase@Inject constructor(private val productsRepository: ProductsRepository) {
    fun getAllProducts(): Single<GetProductsResponse> {
        return productsRepository.getAllProducts()
    }
}