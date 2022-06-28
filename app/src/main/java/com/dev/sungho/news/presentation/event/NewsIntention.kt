package com.dev.sungho.news.presentation.event

import com.dev.sungho.news.foundation.Intention

sealed class NewsIntention : Intention {
    object RefreshClicked : NewsIntention()
}