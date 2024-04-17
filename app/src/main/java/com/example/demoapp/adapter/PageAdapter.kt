package com.example.demoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.MainActivity
import com.example.demoapp.PaginationAdapterCallback
import com.example.demoapp.viewmodels.ModelPost
import com.example.demoapp.databinding.AdapterPostsBinding

class PageAdapter(private var mActivity: MainActivity)  : RecyclerView.Adapter<PageViewHolder>(), PaginationAdapterCallback {

    private val item: Int = 0
    private val loading: Int = 1

    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false

    private var errorMsg: String? = ""

    private var posts : MutableList<ModelPost> = ArrayList();

    fun setPosts(posts : MutableList<ModelPost>) {
        this.posts = posts;
    }

    fun addPosts(posts: MutableList<ModelPost>) {
        for(post in posts){
            addPost(post)
        }
    }
    fun addPost(post: ModelPost) {
        posts.add(post)
        notifyItemInserted(posts.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterPostsBinding.inflate(inflater, parent, false)

        return PageViewHolder(binding)
    }



    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val post = posts[position]
        holder.binding.name.text = "title: "+post.title;
        holder.binding.id.text = "Id: "+post.id.toString();
    }

    override fun getItemCount(): Int {
        return if (posts.size > 0) posts.size else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            item
        }else {
            if (position == posts.size - 1 && isLoadingAdded) {
                loading
            } else {
                item
            }
        }
    }
    override fun retryPageLoad() {
        mActivity.loadNextPage()
    }
}
class PageViewHolder(val binding: AdapterPostsBinding)  : RecyclerView.ViewHolder(binding.root) {
}
