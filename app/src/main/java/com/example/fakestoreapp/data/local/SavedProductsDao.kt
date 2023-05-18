package com.example.fakestoreapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fakestoreapp.data.models.Product

@Dao
interface SavedProductsDao {
    @Query("SELECT * FROM SavedProducts")
    suspend fun getSavedProducts(): List<Product>?

    @Insert
    suspend fun addProduct(product: Product)

    @Query("DELETE FROM SavedProducts WHERE id == :id")
    suspend fun delete(id: Int)
}