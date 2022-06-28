package com.dev.sungho.business

import com.dev.sungho.business.utils.MappingUtil
import com.dev.sungho.data.model.Asset
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MappingUtilTest {
    private val mUtil = MappingUtil()

    /**
     * Testing converting linux timestamp into human readable date format.
     */
    @Test
    fun `Given linux timestamp To string date Then returns readable date format`() {
        val result = mUtil.formatDate(1635818972904L)
        assertThat(result).isEqualTo("02/11/2021 12:09")
    }

    /**
     * Testing order of sorted items and verifying if it is sorted descending order.
     */
    @Test
    fun `Given list of assets To sorting by descending Then returns latest asset first`() {
        val result = mUtil.getSortedAssets(TestUtils.getAssets())
        assertThat(result[0]).isEqualTo(
            Asset(
                id = 1520479143,
                url = "http://www.afr.com/news/politics/national/dancing-is-back-what-you-can-do-in-nsw-from-monday-20211101-p5957r",
                headline = "Dancing is back: what you can do in NSW from Monday",
                theAbstract = "Fully vaccinated Sydneysiders will have most of their freedoms back from Monday, November 8 after the state government brought forward its reopening road map",
                byLine = "",
                timeStamp = 1635813193000,
                relatedImages = TestUtils.getRelatedImages()
            )
        )
    }

    /**
     * Testing the result url from smallest related image lists.
     * The smallest image will be minimum value on width x height from lists.
     */
    @Test
    fun `Given list of related images and want url of smallest image Then returns smallest resolution of url`() {
        val result = mUtil.getSmallestImageUrl(TestUtils.getRelatedImages())
        assertThat(result).isEqualTo("https://www.fairfaxstatic.com.au/content/dam/images/h/1/b/g/4/9/image.related.landscape.1174x783.p5957r.13zzqx.png/1635818974887.jpg")
    }
}