package com.dev.sungho.business.usecases

import com.dev.sungho.business.mapper.NewsResponseMapper
import com.dev.sungho.business.model.DomainResult
import com.dev.sungho.business.model.NewsItem
import com.dev.sungho.data.model.NewsResponse
import com.dev.sungho.data.repository.RemoteRepository
import javax.inject.Inject

/**
 * Use case for fetching news articles
 */
class NewsManagerImpl @Inject constructor(
    private val mRemoteRepository: RemoteRepository,
    private val mNewsMapper: NewsResponseMapper
) : NewsManager {
    @Throws(IllegalStateException::class)
    override suspend fun getNewsArticles(): DomainResult<List<NewsItem>> {
        return try {
            val response = mRemoteRepository.getNewsArticles()
            when {
                response.isSuccess -> handleNewsSuccessResponse(response)
                response.isFailure -> DomainResult.Exception(response.exceptionOrNull()?.message)
                else -> DomainResult.UnknownFailure("Unknown error")
            }
        } catch (e: Exception) {
            DomainResult.Exception(e.message.orEmpty())
        }
    }

    @Throws(IllegalStateException::class)
    private fun handleNewsSuccessResponse(
        response: Result<NewsResponse>
    ): DomainResult<List<NewsItem>> {
        val body = response.getOrNull()
        val domainResult = mNewsMapper.mapToNewsItem(body)
        return DomainResult.Success(domainResult)
    }
}