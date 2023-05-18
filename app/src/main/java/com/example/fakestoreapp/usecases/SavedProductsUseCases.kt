package com.example.fakestoreapp.usecases

import com.example.fakestoreapp.data.models.Product
import com.example.fakestoreapp.data.repositories.ProductsRepository
import javax.inject.Inject

class SavedProductsUseCases @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend fun getAllSavedProducts(): List<Product>? {
        return productsRepository.getAllSavedProducts()
    }
    suspend fun deletedProductFromSavedProducts(product: Product) {
        productsRepository.removeProductFromSavedProducts(product.id)
    }
    suspend fun addProductToSavedProducts(product: Product) {
        productsRepository.addProductToSavedProducts(product)
    }
}