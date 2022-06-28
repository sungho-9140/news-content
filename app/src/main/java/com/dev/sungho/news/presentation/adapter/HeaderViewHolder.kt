package com.dev.sungho.news.presentation.adapter

import com.dev.sungho.business.model.NewsItem
import com.dev.sungho.news.databinding.ListItemHeaderBinding

class HeaderViewHolder(
    private val mBinding: ListItemHeaderBinding
) : NewsViewHolder(mBinding.root) {

    override fun bind(item: NewsItem) {
        val header = item as NewsItem.Header
        with(mBinding) {
            displayName.text = header.displayName
            dateText.text = header.date
        }
    }
}