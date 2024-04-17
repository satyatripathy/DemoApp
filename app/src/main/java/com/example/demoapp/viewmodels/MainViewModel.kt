package com.example.demoapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demoapp.retrofit.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application){

    private var repository: MainRepository? = MainRepository(application);


    var _topPageResponse = MutableLiveData<List<ModelPost>>()
    var _nextPageResponse = MutableLiveData<List<ModelPost>>()
    var errorMessage = MutableLiveData<Any>()


    fun getFirstPosts(page : Int) {
        repository!!.loadPage(_topPageResponse , errorMessage, page)
    }

    fun getNextPosts(page : Int) {
        repository!!.loadPage(_nextPageResponse , errorMessage, page)
    }

    val firstPostsResp : MutableLiveData<List<ModelPost>>
        get() = _topPageResponse
    val nextPostsResp : MutableLiveData<List<ModelPost>>
        get() = _nextPageResponse

    val error : MutableLiveData<Any>
        get() = errorMessage
}