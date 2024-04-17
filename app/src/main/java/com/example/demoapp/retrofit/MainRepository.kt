package com.example.demoapp.retrofit

import android.app.Application
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.lifecycle.MutableLiveData
import com.example.demoapp.viewmodels.ModelPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainRepository (app: Application) {

    private var instanceApi: API
    init {
        APIService.init(app)
        instanceApi= APIService.instance
    }
    companion object{
        private var mainRepository: MainRepository?=null
        @Synchronized
        fun getInstance(app: Application): MainRepository? {
            if (mainRepository == null) {
                mainRepository = MainRepository(app)
            }
            return mainRepository
        }
    }

    fun loadPage(postsResp: MutableLiveData<List<ModelPost>>, error: MutableLiveData<Any>, page : Int) {
        val response = instanceApi.getAllPosts(userId = page)
        response.enqueue(object : Callback<List<ModelPost>> {
            override fun onResponse(call: Call<List<ModelPost>>, response: Response<List<ModelPost>>) {
                postsResp.postValue(response.body())
            }

            override fun onFailure(call: Call<List<ModelPost>>, t: Throwable) {
                error.postValue(t.message)
            }
        })
    }
}





