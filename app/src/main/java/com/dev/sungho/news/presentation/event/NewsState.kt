package com.dev.sungho.news.presentation.event

import com.dev.sungho.business.model.NewsItem
import com.dev.sungho.news.foundation.ViewState

sealed class NewsState : ViewState {
    object Loading : NewsState()
    data class Success(val result: List<NewsItem>) : NewsState()
    data class Error(val message: String? = null) : NewsState()
}