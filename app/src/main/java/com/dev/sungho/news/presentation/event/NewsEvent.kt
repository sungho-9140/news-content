package com.dev.sungho.news.presentation.event

import com.dev.sungho.news.foundation.Event

sealed class NewsEvent : Event {
    object HidePage : NewsEvent()
}