package com.example.fakestoreapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.fakestoreapp.data.local.FakeStoreDatabase
import com.example.fakestoreapp.data.remote.ApiService
import com.example.fakestoreapp.data.remote.NetworkManager
import com.example.fakestoreapp.utils.NetworkConstants
import com.example.imageclassification.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

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
    @Singleton
    @Provides
    fun provideSavedProductsDao(db: FakeStoreDatabase) = db.savedProductsDao()

    @Provides
    fun provideBaseUrl() = NetworkConstants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Accept-Language","en")
                        .build()
                    chain.proceed(request)
                })
                .addInterceptor(loggingInterceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS).build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Accept-Language","en")
                        .build()
                    chain.proceed(request)
                })
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES).build()
        }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideNetworkDataSource(apiService: ApiService): NetworkManager {
        return NetworkManager(apiService)
    }
}