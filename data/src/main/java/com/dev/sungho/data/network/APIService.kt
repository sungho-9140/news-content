package com.dev.sungho.data.network

import com.dev.sungho.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("/1/coding_test/13ZZQX/full")
    suspend fun getNewsArticles(): Response<NewsResponse>
}