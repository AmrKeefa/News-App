package com.clicks.newsapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clicks.newsapp.R
import com.clicks.newsapp.data.model.NewsResponse.Article
import com.clicks.newsapp.utils.extensions.load
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news.*

class NewsRecyclerAdapter() :
    RecyclerView.Adapter<NewsRecyclerAdapter.RecyclerViewViewHolder>() {
    private var items = mutableListOf<Article>()

    fun setData(items: List<Article>) {
        this.items.clear()
        this.items.addAll(items)
    }
    fun clear() {
        items.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

    private var itemCallback: ((Article) -> Unit)? = null

    fun setItemCallBack(itemCallback: (Article?) -> Unit) {
        this.itemCallback = itemCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false)
        return RecyclerViewViewHolder(itemView, itemCallback)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
        currentItem.urlToImage.let {
            if (it != null) {
                holder.newsImageView.load(it)
            }
        }
        holder.newsTitleTextView.text = currentItem.title
        holder.sourceNameTextView.text = currentItem.source?.name

    }

    override fun getItemCount(): Int = items.size

    class RecyclerViewViewHolder(
        override val containerView: View,
        private val itemCallback: ((Article) -> Unit)?
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(article: Article) {
            containerView.setOnClickListener { itemCallback?.invoke(article) }
        }
    }
}