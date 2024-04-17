package com.example.demoapp.retrofit

import com.example.demoapp.viewmodels.ModelPost
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do : Api Interface of Retrofit Client
 * Date 11/23/2020 - 11:46 AM
 */
interface API {

    @GET("posts")
    fun getAllPosts(@Query("userId") userId: Int): Call<List<ModelPost>>
}