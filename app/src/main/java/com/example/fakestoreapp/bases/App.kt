package com.example.fakestoreapp.bases

import android.app.Application
import android.content.Context
import com.example.fakestoreapp.utils.SingletonHolderWithoutContext
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {


    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object : SingletonHolderWithoutContext<App>(::App) {
        var applicationContext: Context? = null
    }

}