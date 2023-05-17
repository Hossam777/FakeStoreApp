package com.example.fakestoreapp.data.remote.responses

import com.example.fakestoreapp.data.models.Product

data class GetProductsResponse(
    val products: List<Product>
)