package com.dev.sungho.business.utils

import com.dev.sungho.business.utils.Constants.Companion.PATTERN
import com.dev.sungho.data.model.Asset
import com.dev.sungho.data.model.RelatedImage
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Utility class for specific business requirements
 */
class MappingUtil {
    /**
     * Converting milli time stamp into readable date format
     */
    fun formatDate(timeStamp: Long): String {
        val data = Date(TimeUnit.MILLISECONDS.toMillis(timeStamp))
        return SimpleDateFormat(PATTERN, Locale.getDefault()).format(data)
    }

    /**
     * Sorting latest articles
     */
    fun getSortedAssets(assets: List<Asset>): List<Asset> =
        assets.sortedByDescending { it.timeStamp }

    /**
     * Try getting an url which has smallest resolution (width * height)
     */
    @Throws(IllegalStateException::class)
    fun getSmallestImageUrl(images: List<RelatedImage>?): String? =
        images?.minByOrNull { checkNotNull(it.width) * checkNotNull(it.height) }?.url
}