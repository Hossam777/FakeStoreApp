package com.example.fakestoreapp.utils


import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException
import java.io.InputStream
import java.net.URLEncoder

object JsonConverter {

    fun loadJSONFromAsset(context: Context, fileName: String): String? {
        var json: String? = null
        json = try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun <T : Any> convertJsonToObj(json: String, classType: Class<T>): T? {
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<T> = moshi.adapter(classType)
        return adapter.fromJson(json)
    }

    fun <T : Any> convertJsonToArray(json: String, classType: Class<T>): List<T>? {
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(
            MutableList::class.java,
            classType
        )
        val adapter: JsonAdapter<List<T>> = moshi.adapter(type)
        return adapter.fromJson(json)

    }

}