package com.example.fakestoreapp.di

import com.example.fakestoreapp.data.local.SavedProductsDao
import com.example.fakestoreapp.data.remote.NetworkManager
import com.example.fakestoreapp.data.repositories.ProductsRepository
import com.example.fakestoreapp.ui.home.HomeViewModel
import com.example.fakestoreapp.usecases.GetAllCategoriesUseCase
import com.example.fakestoreapp.usecases.GetProductsUseCase
import com.example.fakestoreapp.usecases.SavedProductsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object HomeModule {
    @Provides
    fun provideProductsRepository(networkManager: NetworkManager, savedProductsDao: SavedProductsDao): ProductsRepository
    = ProductsRepository(networkManager, savedProductsDao)

    @Provides
    fun provideGetProductsUseCase(productsRepository: ProductsRepository): GetProductsUseCase
    = GetProductsUseCase(productsRepository)

    @Provides
    fun provideSavedProductsUseCases(productsRepository: ProductsRepository): SavedProductsUseCases
            = SavedProductsUseCases(productsRepository)
    @Provides
    fun provideGetAllCategoriesUseCase(productsRepository: ProductsRepository): GetAllCategoriesUseCase
            = GetAllCategoriesUseCase(productsRepository)
    fun provideHomeViewModel(
        getProductsUseCase: GetProductsUseCase,
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
        savedProductsUseCases: SavedProductsUseCases): HomeViewModel
    = HomeViewModel(getProductsUseCase, getAllCategoriesUseCase, savedProductsUseCases)
}