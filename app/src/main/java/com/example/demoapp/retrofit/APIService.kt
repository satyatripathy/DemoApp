package com.example.demoapp.retrofit

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private var context : Context? = null
    fun init(context : Context) { APIService.context = context   }

    private fun apiService(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
    val instance : API by lazy { apiService().create(API::class.java) }
}