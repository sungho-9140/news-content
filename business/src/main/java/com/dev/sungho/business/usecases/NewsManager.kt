package com.dev.sungho.business.usecases

import com.dev.sungho.business.model.DomainResult
import com.dev.sungho.business.model.NewsItem

interface NewsManager {
    /**
     * Manager for fetching news list from repository
     * <p>
     * Once response fetched from repository, we are converting response data into
     * domain object in mapper utility.
     */
    suspend fun getNewsArticles(): DomainResult<List<NewsItem>>
}