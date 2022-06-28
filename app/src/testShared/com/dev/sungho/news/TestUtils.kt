package com.dev.sungho.news

import com.dev.sungho.business.model.NewsItem
import com.dev.sungho.data.model.Asset
import com.dev.sungho.data.model.NewsResponse
import com.dev.sungho.data.model.RelatedImage

/**
 * This utility class will be used for testing purpose
 */
class TestUtils {
    companion object {
        fun getNewsItem(): List<NewsItem> =
            arrayListOf(
                NewsItem.Header(
                    id = 67184313L,
                    displayName = "AFR iPad Editor's Choice",
                    date = "02/11/2021 1:09",
                    localTime = 1234L
                ),
                NewsItem.Article(
                    id = 1520479143L,
                    url = "http://www.afr.com/news/politics/national/dancing-is-back-what-you-can-do-in-nsw-from-monday-20211101-p5957r",
                    title = "Dancing is back: what you can do in NSW from Monday",
                    subtitle = "",
                    description = "Fully vaccinated Sydneysiders will have most of their freedoms back from Monday, November 8 after the state government brought forward its reopening road map",
                    date = "02/11/2021 11:33",
                    imageUrl = "https://www.fairfaxstatic.com.au/content/dam/images/h/1/b/g/4/9/image.related.landscape.1174x783.p5957r.13zzqx.png/1635818974887.jpg"
                )
            )

        private fun getRelatedImages(): List<RelatedImage> =
            arrayListOf(
                RelatedImage(
                    id = 1030138713,
                    url = "https://www.fairfaxstatic.com.au/content/dam/images/h/1/b/g/4/9/image.related.wideLandscape.1500x844.p5957r.13zzqx.png/1635818974887.jpg",
                    width = 1500,
                    height = 844
                ),
                RelatedImage(
                    id = 1030138714,
                    url = "https://www.fairfaxstatic.com.au/content/dam/images/h/1/b/g/4/9/image.related.landscape.1174x783.p5957r.13zzqx.png/1635818974887.jpg",
                    width = 1174,
                    height = 783
                )
            )

        /**
         * Retrieving asset response
         */
        private fun getAssets(): List<Asset> =
            arrayListOf(
                Asset(
                    id = 1520479143,
                    url = "http://www.afr.com/news/politics/national/dancing-is-back-what-you-can-do-in-nsw-from-monday-20211101-p5957r",
                    headline = "Dancing is back: what you can do in NSW from Monday",
                    theAbstract = "Fully vaccinated Sydneysiders will have most of their freedoms back from Monday, November 8 after the state government brought forward its reopening road map",
                    byLine = "",
                    timeStamp = 1635813193000,
                    relatedImages = getRelatedImages()
                ),
                Asset(
                    id = 1520479145,
                    url = "http://www.afr.com/brand/life-and-leisure/the-hyundai-electric-car-with-surprising-star-power-20211022-p592at",
                    headline = "The Hyundai electric car with surprising star power",
                    theAbstract = "The refreshingly original Ioniq5, a fully electric mega-hatch, takes full advantage of a dedicated EV platform to break the mould in car design",
                    byLine = "Tony Davis",
                    timeStamp = 1635962400000,
                    relatedImages = null
                )
            )

        /**
         * Retrieving news response
         */
        fun getNewsResponse(): NewsResponse =
            NewsResponse(
                id = 67184313,
                displayName = "AFR iPad Editor's Choice",
                assets = getAssets(),
                timeStamp = 1635818972904
            )
    }
}