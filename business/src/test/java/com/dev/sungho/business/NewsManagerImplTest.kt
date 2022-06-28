package com.dev.sungho.business

import com.dev.sungho.business.mapper.NewsResponseMapper
import com.dev.sungho.business.model.DomainResult
import com.dev.sungho.business.model.NewsItem
import com.dev.sungho.business.usecases.NewsManager
import com.dev.sungho.business.usecases.NewsManagerImpl
import com.dev.sungho.data.repository.RemoteRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class NewsManagerImplTest {
    @get:Rule
    var mCoroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var mRemoteRepository: RemoteRepository

    @Mock
    private lateinit var mNewsResponseMapper: NewsResponseMapper

    private lateinit var mNewsManager: NewsManager

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        mNewsManager = NewsManagerImpl(mRemoteRepository, mNewsResponseMapper)
    }

    /**
     * Assuming business manager request data and fetched successfully.
     * After that verifying if first item of domain list is Header, and the rest of items are Article.
     * And also verifying if each fields have expected values.
     */
    @Test
    fun `Given manager request data When repository returned and mapped successfully Then result must have news items`() {
        mCoroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mRemoteRepository.getNewsArticles()).thenReturn(Result.success(TestUtils.getNewsResponse()))
            whenever(mNewsResponseMapper.mapToNewsItem(any())).thenReturn(TestUtils.getNewsItem())

            val response = mNewsManager.getNewsArticles() as DomainResult.Success

            assertThat(response.item[0]).isInstanceOf(NewsItem.Header::class.java)
            assertThat(response.item[1]).isInstanceOf(NewsItem.Article::class.java)

            val header = response.item[0] as NewsItem.Header
            header.run {
                assertThat(id).isEqualTo(67184313L)
                assertThat(displayName).isEqualTo("AFR iPad Editor's Choice")
                assertThat(date).isEqualTo("02/11/2021 1:09")
            }

            val article = response.item[1] as NewsItem.Article
            article.run {
                assertThat(id).isEqualTo(1520479143L)
                assertThat(url).isEqualTo("http://www.afr.com/news/politics/national/dancing-is-back-what-you-can-do-in-nsw-from-monday-20211101-p5957r")
                assertThat(title).isEqualTo("Dancing is back: what you can do in NSW from Monday")
                assertThat(description).isEqualTo("Fully vaccinated Sydneysiders will have most of their freedoms back from Monday, November 8 after the state government brought forward its reopening road map")
                assertThat(subtitle).isEqualTo("")
                assertThat(date).isEqualTo("02/11/2021 11:33")
                assertThat(imageUrl).isEqualTo("https://www.fairfaxstatic.com.au/content/dam/images/h/1/b/g/4/9/image.related.landscape.1174x783.p5957r.13zzqx.png/1635818974887.jpg")
            }
        }
    }

    /**
     * Assuming that business request has an exception from repository (data layer)
     * and verifying if the exception is passing through to downstream.
     */
    @Test
    fun `Given manager request data When repository has failed Then result should contain failure`() {
        mCoroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mRemoteRepository.getNewsArticles()).thenReturn(
                Result.failure(Exception("Some Exception"))
            )

            val response = mNewsManager.getNewsArticles() as DomainResult.Exception
            assertThat(response.message).isEqualTo("Some Exception")
        }
    }

    /**
     * Assuming that business request completed successfully, but there is an exception at mapper.
     * Then verifying if the exception thrown from the mapper will return to downstream.
     */
    @Test
    fun `Given manager request data When mapper has exception Then result fails with exception`() {
        mCoroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mRemoteRepository.getNewsArticles()).thenReturn(Result.success(TestUtils.getNewsResponse()))
            whenever(mNewsResponseMapper.mapToNewsItem(any())).thenThrow(NullPointerException("Mapper Exception"))

            val response = mNewsManager.getNewsArticles() as DomainResult.Exception
            assertThat(response.message).isEqualTo("Mapper Exception")
        }
    }
}