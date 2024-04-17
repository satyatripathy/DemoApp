package com.example.demoapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoapp.adapter.PageAdapter
import com.example.demoapp.databinding.ActivityMainBinding
import com.example.demoapp.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding : ActivityMainBinding
    private lateinit var homeViewModel: MainViewModel
    private lateinit var mAdapter: PageAdapter
    private val pageStart: Int = 1
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var totalPages: Int = 100
    private var currentPage: Int = pageStart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.activity = this
        setContentView(binding.root)

        homeViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)

//        homeViewModel.posts.observe(this, Observer {
//            Log.d(TAG, "onCreate: $it")
//            adapter.setPosts(it.toMutableList())
//        })
//
//        homeViewModel.errorMessage.observe(this, Observer {})
//        homeViewModel.getAllPosts(1)

        initMyOrderRecyclerView()
        observerDataRequest()
    }

   private fun initMyOrderRecyclerView() {
       mAdapter = PageAdapter(this@MainActivity)
       binding.pageAdapter = mAdapter
       binding.recyclerMyOrders.setHasFixedSize(true)
//       binding.recyclerMyOrders.itemAnimator = DefaultItemAnimator()

       loadFirstPage()

       binding.recyclerMyOrders.addOnScrollListener(object : PaginationScrollListener(binding.recyclerMyOrders.layoutManager as LinearLayoutManager) {
           override fun loadMoreItems() {
               isLoading = true
               binding.mainProgress.visibility = View.GONE
               currentPage += 1

               Handler(Looper.myLooper()!!).postDelayed({
                   loadNextPage()
               }, 1000)
           }

           override fun getTotalPageCount(): Int {
               return totalPages
           }

           override fun isLastPage(): Boolean {
               return isLastPage
           }

           override fun isLoading(): Boolean {
               return isLoading
           }
       })
   }

    private fun loadFirstPage() {
        homeViewModel.getFirstPosts(currentPage)
    }

    fun loadNextPage() {
        binding.mainProgress.visibility = View.VISIBLE
        homeViewModel.getNextPosts(currentPage)
    }

    private fun observerDataRequest(){
        homeViewModel.firstPostsResp.observe(this) {
            binding.mainProgress.visibility = View.GONE
            isLoading = false
            mAdapter.addPosts(it.toMutableList())
        }

        homeViewModel.nextPostsResp.observe(this) {
            binding.mainProgress.visibility = View.GONE
            isLoading = false
            mAdapter.addPosts(it.toMutableList())
        }

        homeViewModel.error.observe(this) {
            //TODO show error
        }
    }
}
