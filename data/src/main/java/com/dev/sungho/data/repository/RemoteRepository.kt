package com.dev.sungho.data.repository

import com.dev.sungho.data.model.NewsResponse

interface RemoteRepository {
    suspend fun getNewsArticles(): Result<NewsResponse>
}