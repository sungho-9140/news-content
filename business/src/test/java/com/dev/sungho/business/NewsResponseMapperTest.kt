package com.dev.sungho.business

import com.dev.sungho.business.mapper.NewsResponseMapper
import com.dev.sungho.business.utils.MappingUtil
import com.dev.sungho.data.model.Asset
import com.dev.sungho.data.model.NewsResponse
import com.dev.sungho.data.model.RelatedImage
import org.assertj.core.api.Assertions
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Verifying if non-nullable fields have nullable value,
 * then should throw exception.
 */
class NewsResponseMapperTest {
    private val mUtil = MappingUtil()
    private val mResponseMapper = NewsResponseMapper(mUtil)

    @Test
    fun `When header id is null Then should throw exception`() {
        val response = getNewsResponse(isValidId = false)
        assertThrowsIllegalStateException(response)
    }

    @Test
    fun `When header display name is null Then should throw exception`() {
        val response = getNewsResponse(isValidDisplayName = false)
        assertThrowsIllegalStateException(response)
    }

    @Test
    fun `When header timestamp is null Then should throw exception`() {
        val response = getNewsResponse(isValidDate = false)
        assertThrowsIllegalStateException(response)
    }

    @Test
    fun `When header assets is null Then should throw exception`() {
        val response = getNewsResponse(isValidAsset = false)
        assertThrowsIllegalStateException(response)
    }

    @Test
    fun `When asset id is null Then should throw exception`() {
        val response = getNewsResponse(
            assets = getAssets(
                isValidId = false
            )
        )
        assertThrowsIllegalStateException(response)
    }

    @Test
    fun `When asset timestamp is null Then should throw exception`() {
        val response = getNewsResponse(
            assets = getAssets(
                isValidDate = false
            )
        )
        assertThrowsIllegalStateException(response)
    }

    @Test
    fun `When asset title is null Then should throw exception`() {
        val response = getNewsResponse(
            assets = getAssets(
                isValidTitle = false
            )
        )
        assertThrowsIllegalStateException(response)
    }

    @Test
    fun `When asset subtitle is null Then should throw exception`() {
        val response = getNewsResponse(
            assets = getAssets(
                isValidSubtitle = false
            )
        )
        assertThrowsIllegalStateException(response)
    }

    @Test
    fun `When asset description is null Then should throw exception`() {
        val response = getNewsResponse(
            assets = getAssets(
                isValidDescription = false
            )
        )
        assertThrowsIllegalStateException(response)
    }

    @Test
    fun `When asset url is null Then should throw exception`() {
        val response = getNewsResponse(
            assets = getAssets(
                isValidUrl = false
            )
        )
        assertThrowsIllegalStateException(response)
    }

    @Test
    fun `When asset images is null Then allow it`() {
        val response = getNewsResponse(
            assets = getAssets(
                isValidImages = false
            )
        )
        assertNotNull(response)
    }

    /**
     * Reusable method for creating [NewsResponse] object for testing
     */
    private fun getNewsResponse(
        isValidId: Boolean = true,
        isValidDisplayName: Boolean = true,
        isValidDate: Boolean = true,
        isValidAsset: Boolean = true,
        assets: List<Asset> = getAssets()

    ) = NewsResponse(
        id = if (isValidId) 100L else null,
        displayName = if (isValidDisplayName) "display name" else null,
        timeStamp = if (isValidDate) 10000L else null,
        assets = if (isValidAsset) assets else null
    )

    /**
     * Reusable method for creating [Asset] object for testing
     */
    private fun getAssets(
        isValidId: Boolean = true,
        isValidUrl: Boolean = true,
        isValidTitle: Boolean = true,
        isValidSubtitle: Boolean = true,
        isValidDescription: Boolean = true,
        isValidDate: Boolean = true,
        isValidImages: Boolean = true,
        images: List<RelatedImage> = getImages()
    ): List<Asset> = listOf(
        Asset(
            id = if (isValidId) 100L else null,
            url = if (isValidUrl) "www.some-url.com" else null,
            headline = if (isValidTitle) "title" else null,
            byLine = if (isValidSubtitle) "author" else null,
            theAbstract = if (isValidDescription) "desc" else null,
            timeStamp = if (isValidDate) 1000L else null,
            relatedImages = if (isValidImages) images else null,
        )
    )

    /**
     * Reusable method for creating [RelatedImage] object for testing
     */
    private fun getImages(
        isValidId: Boolean = true,
        isValidUrl: Boolean = true,
        isValidWidth: Boolean = true,
        isValidHeight: Boolean = true
    ): List<RelatedImage> = listOf(
        RelatedImage(
            id = if (isValidId) 100L else null,
            url = if (isValidUrl) "www.some-url.com" else null,
            width = if (isValidWidth) 640 else null,
            height = if (isValidHeight) 480 else null
        )
    )

    /**
     * Verifying that if the result of mapper returns [IllegalStateException],
     * which is defined at code level.
     */
    private fun assertThrowsIllegalStateException(response: NewsResponse) {
        Assertions.assertThatThrownBy {
            mResponseMapper.mapToNewsItem(response)
        }.isInstanceOf(IllegalStateException::class.java)
    }
}

