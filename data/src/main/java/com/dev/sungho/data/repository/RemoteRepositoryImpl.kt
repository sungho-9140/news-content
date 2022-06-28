package com.dev.sungho.data.repository

import com.dev.sungho.data.model.NewsResponse
import com.dev.sungho.data.network.APIService
import javax.inject.Inject

/**
 * Dependency injected for Singleton RemoteRepository.
 * This instance will be used multiple times from several api manager or any business logic.
 * This will has same hashcode, which means it will be same instance used when calling from
 * different locations.
 */
class RemoteRepositoryImpl @Inject constructor(
    private val mAPIService: APIService
) : RemoteRepository {
    /**
     * Fetching news articles
     */
    override suspend fun getNewsArticles(): Result<NewsResponse> {
        val response = mAPIService.getNewsArticles()
        val body = response.body()
        return when {
            response.isSuccessful && body != null -> Result.success(body)
            else -> Result.failure(
                Exception("Server error: code:${response.code()}, message:${response.message()}")
            )
        }
    }
}