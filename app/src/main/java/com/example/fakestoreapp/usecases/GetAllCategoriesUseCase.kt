package com.example.fakestoreapp.usecases

import com.example.fakestoreapp.data.repositories.ProductsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAllCategoriesUseCase@Inject constructor(private val productsRepository: ProductsRepository) {
    fun getAllCategories(): Single<List<String>> {
        return productsRepository.getAllCategories()
    }
}