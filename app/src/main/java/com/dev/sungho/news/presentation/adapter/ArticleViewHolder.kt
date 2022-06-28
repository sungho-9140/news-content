package com.dev.sungho.news.presentation.adapter

import android.view.View
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.dev.sungho.business.model.NewsItem
import com.dev.sungho.news.databinding.ListItemArticleBinding
import com.dev.sungho.news.presentation.article.NewsFragmentDirections

class ArticleViewHolder(
    private val mBinding: ListItemArticleBinding
) : NewsViewHolder(mBinding.root) {

    override fun bind(item: NewsItem) {
        val article = item as NewsItem.Article

        with(mBinding) {
            title.text = article.title
            subtitle.text = article.subtitle
            description.text = article.description
            date.text = article.date

            article.imageUrl?.let { url ->
                Glide.with(root.context)
                    .load(url)
                    .into(imgThumbnail)

                imgThumbnail.visibility = View.VISIBLE
            } ?: run {
                imgThumbnail.visibility = View.GONE
            }

            root.setOnClickListener { view ->
                view.findNavController().navigate(NewsFragmentDirections
                    .actionNewsFragmentToDetailFragment(article.url))
            }
        }
    }
}