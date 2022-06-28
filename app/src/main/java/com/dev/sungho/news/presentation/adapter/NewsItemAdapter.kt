package com.dev.sungho.news.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dev.sungho.business.model.NewsItem

const val ITEM_VIEW_TYPE_HEADER = 0
const val ITEM_VIEW_TYPE_ARTICLE = 1

class NewsItemAdapter : ListAdapter<NewsItem, NewsViewHolder>(
    ITEM_COMPARATOR
) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NewsItem.Header -> ITEM_VIEW_TYPE_HEADER
            is NewsItem.Article -> ITEM_VIEW_TYPE_ARTICLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder.newInstance(parent, viewType)

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<NewsItem>() {
            override fun areItemsTheSame(
                oldItem: NewsItem,
                newItem: NewsItem
            ): Boolean {
                val compHeader = (oldItem is NewsItem.Header && newItem is NewsItem.Header && oldItem.id == newItem.id)
                val compItem = (oldItem is NewsItem.Article && newItem is NewsItem.Article && oldItem.id == newItem.id)
                return compHeader || compItem
            }

            override fun areContentsTheSame(
                oldItem: NewsItem,
                newItem: NewsItem
            ): Boolean = oldItem == newItem
        }
    }
}