package com.dev.sungho.news

import com.dev.sungho.data.model.NewsResponse
import com.dev.sungho.data.repository.RemoteRepository

/**
 * This fake repository will be replaced with actual [RemoteRepository] at data module.
 * and will return mock response for UI automation test.
 */
class FakeRemoteRepository : RemoteRepository {
    override suspend fun getNewsArticles(): Result<NewsResponse> =
        Result.success(TestUtils.getNewsResponse())
}