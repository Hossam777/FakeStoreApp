package com.example.fakestoreapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.fakestoreapp.data.local.FakeStoreDatabase
import com.example.fakestoreapp.utils.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }
    @Provides
    @Singleton
    fun provideUserNetworkManager(context: Context): NetworkManager =
        NetworkManager(context)

    @Singleton
    @Provides
    fun provideFakeStoreDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        FakeStoreDatabase::class.java,
        "fake_store_database"
    ).build()

    @Singleton
    @Provides
    fun provideUserDao(db: FakeStoreDatabase) = db.userDao()
}