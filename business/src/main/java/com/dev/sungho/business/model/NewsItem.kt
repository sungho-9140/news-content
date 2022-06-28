package com.dev.sungho.business.model

sealed class NewsItem {
    data class Header(
        /**
         * As StateFlow doesn't emit same value, we need to use localTime for differentiate object
         */
        val localTime: Long,
        val date: String,
        val id: Long,
        val displayName: String
    ) : NewsItem()

    data class Article(
        val date: String,
        val id: Long,
        val url: String,
        val title: String,
        val subtitle: String,
        val description: String,
        val imageUrl: String?
    ) : NewsItem()
}