package com.dev.sungho.business.mapper

import com.dev.sungho.business.model.NewsItem
import com.dev.sungho.business.utils.MappingUtil
import com.dev.sungho.data.model.Asset
import com.dev.sungho.data.model.NewsResponse
import java.util.*
import javax.inject.Inject

/**
 * Mapper class for converting response data object into domain object
 * <p>
 * This mapper is for following reasons
 * 1. Checking nullable / non-nullable field from business requirements.
 * 2. Mapping into domain object for later use.
 */
class NewsResponseMapper @Inject constructor(
    private val mUtil: MappingUtil
) {
    @Throws(IllegalStateException::class)
    fun mapToNewsItem(response: NewsResponse?): List<NewsItem> {
        checkNotNull(response)
        val assets = checkNotNull(response.assets)
        val newsInfo = listOf(
            NewsItem.Header(
                localTime = Calendar.getInstance().timeInMillis,
                date = mUtil.formatDate(checkNotNull(response.timeStamp)),
                id = checkNotNull(response.id),
                displayName = checkNotNull(response.displayName)
            )
        )
        val sortByDate = mUtil.getSortedAssets(assets)
        return newsInfo + mapToArticles(sortByDate.toList())
    }

    @Throws(IllegalStateException::class)
    private fun mapToArticles(assets: List<Asset>): List<NewsItem> =
        assets.map { asset ->
            NewsItem.Article(
                id = checkNotNull(asset.id),
                url = checkNotNull(asset.url),
                title = checkNotNull(asset.headline),
                subtitle = checkNotNull(asset.byLine),
                description = checkNotNull(asset.theAbstract),
                date = mUtil.formatDate(checkNotNull(asset.timeStamp)),
                imageUrl = mUtil.getSmallestImageUrl(asset.relatedImages)
            )
        }
}