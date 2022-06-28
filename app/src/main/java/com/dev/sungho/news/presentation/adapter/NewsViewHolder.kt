package com.dev.sungho.news.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.sungho.business.model.NewsItem
import com.dev.sungho.news.databinding.ListItemArticleBinding
import com.dev.sungho.news.databinding.ListItemHeaderBinding

abstract class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    /**
     * As each of view holder is likely to have different layout.
     * we divide this into multiple view holders.
     */
    companion object {
        fun newInstance(parent: ViewGroup, viewType: Int): NewsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                ITEM_VIEW_TYPE_HEADER ->
                    HeaderViewHolder(ListItemHeaderBinding.inflate(inflater, parent, false))
                ITEM_VIEW_TYPE_ARTICLE ->
                    ArticleViewHolder(ListItemArticleBinding.inflate(inflater, parent, false))
                else ->
                    throw IllegalArgumentException("Invalid View Type [$viewType]")
            }
        }
    }

    abstract fun bind(item: NewsItem)
}