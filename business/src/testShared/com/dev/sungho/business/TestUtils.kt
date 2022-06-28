package com.dev.sungho.business

import com.dev.sungho.business.model.NewsItem
import com.dev.sungho.data.model.Asset
import com.dev.sungho.data.model.NewsResponse
import com.dev.sungho.data.model.RelatedImage

/**
 * Returning mock response for business layer unit test
 */
class TestUtils {
    companion object {
        fun getRelatedImages(): List<RelatedImage> =
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
        fun getAssets(): List<Asset> =
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
                    id = 1520479144,
                    url = "ttp://www.afr.com/brand/chanticleer/housing-haves-and-have-nots-worry-westpacs-king-20211101-p5956r",
                    headline = "Housing ‘haves’ and ‘have-nots’ worry Westpac’s King",
                    theAbstract = "Boss Peter King says the financial health of those with a home is good. It’s those trying to break into the market he worries about.",
                    byLine = "James Thomson",
                    timeStamp = 1635809514000,
                    relatedImages = getRelatedImages()
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

        /**
         * Retrieving domain news item
         */
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
    }
}