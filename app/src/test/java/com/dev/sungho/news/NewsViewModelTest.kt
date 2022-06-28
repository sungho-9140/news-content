package com.dev.sungho.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dev.sungho.business.model.DomainResult
import com.dev.sungho.business.model.NewsItem
import com.dev.sungho.business.usecases.NewsManager
import com.dev.sungho.news.presentation.article.NewsViewModel
import com.dev.sungho.news.presentation.event.NewsIntention
import com.dev.sungho.news.presentation.event.NewsState
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * More unit tests available at business and data module. Please take a look at them.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {
    /**
     * JUnit rule that configures LiveData to execute each task synchronously
     */
    @get:Rule
    val mInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mCoroutinesTestRule = CoroutineTestRule()

    /**
     * Mocks
     */
    @Mock
    private lateinit var mNewsManager: NewsManager

    /**
     * Fields
     */
    private lateinit var mViewModel: NewsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    /**
     * As we emit first view state when view model creation, we need to pause dispatcher
     * and need to collect [Loading] state first, and then, resume dispatcher to get actual fetched items.
     *
     * 1. Testing lifecycle of view model and verifying returned values
     * 2. Testing view state updated in the event of refresh clicked.
     */
    @Test
    fun `Given model request response When request successfully loaded Then should return news items`() {
        mCoroutinesTestRule.testDispatcher.runBlockingTest {
            val response = TestUtils.getNewsItem()
            whenever(mNewsManager.getNewsArticles()).thenReturn(DomainResult.Success(response))

            // Given coroutines have not started yet and the view model is created
            pauseDispatcher()

            // Create ViewModel
            createViewModel()

            // Then the first result has been emitted
            assertThat(mViewModel.viewState.value).isEqualTo(NewsState.Loading)

            // When coroutine starts
            resumeDispatcher()

            // Then the next result has been emitted
            val result = mViewModel.viewState.value
            assertThat(result).isInstanceOf(NewsState.Success::class.java)
            val item = result as NewsState.Success

            val value = item.result
            assertThat(value[0]).isInstanceOf(NewsItem.Header::class.java)
            assertThat(value[1]).isInstanceOf(NewsItem.Article::class.java)

            val header = value[0] as NewsItem.Header
            header.run {
                assertThat(id).isEqualTo(67184313L)
                assertThat(displayName).isEqualTo("AFR iPad Editor's Choice")
                assertThat(date).isEqualTo("02/11/2021 1:09")
            }

            val article = value[1] as NewsItem.Article
            article.run {
                assertThat(id).isEqualTo(1520479143L)
                assertThat(url).isEqualTo("http://www.afr.com/news/politics/national/dancing-is-back-what-you-can-do-in-nsw-from-monday-20211101-p5957r")
                assertThat(title).isEqualTo("Dancing is back: what you can do in NSW from Monday")
                assertThat(description).isEqualTo("Fully vaccinated Sydneysiders will have most of their freedoms back from Monday, November 8 after the state government brought forward its reopening road map")
                assertThat(subtitle).isEqualTo("")
                assertThat(date).isEqualTo("02/11/2021 11:33")
                assertThat(imageUrl).isEqualTo("https://www.fairfaxstatic.com.au/content/dam/images/h/1/b/g/4/9/image.related.landscape.1174x783.p5957r.13zzqx.png/1635818974887.jpg")
            }

            // Refresh event
            mViewModel.onIntention(NewsIntention.RefreshClicked)
            assertThat(mViewModel.viewState.value).isInstanceOf(NewsState.Success::class.java)
        }
    }

    /**
     * Testing if data request failed for some reason, then verifying if it returns error.
     */
    @Test
    fun `Given model request data When manager has failed Then result should return exception`() {
        mCoroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mNewsManager.getNewsArticles()).thenReturn(DomainResult.Exception("Some Exception"))

            pauseDispatcher()
            createViewModel()
            assertThat(mViewModel.viewState.value).isEqualTo(NewsState.Loading)
            resumeDispatcher()
            val result = mViewModel.viewState.value
            assertThat(result).isEqualTo(NewsState.Error("Some Exception"))
            assertThat(result).isInstanceOf(NewsState.Error::class.java)
        }
    }

    /**
     * Testing if data request failed from an unknown reason, then verifying if it returns error.
     */
    @Test
    fun `Given model request data When manager run into unknown error Then result should return error`() {
        mCoroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mNewsManager.getNewsArticles()).thenReturn(DomainResult.UnknownFailure())

            pauseDispatcher()
            createViewModel()
            assertThat(mViewModel.viewState.value).isEqualTo(NewsState.Loading)
            resumeDispatcher()
            val result = mViewModel.viewState.value
            assertThat(result).isEqualTo(NewsState.Error("Unknown Error"))
            assertThat(result).isInstanceOf(NewsState.Error::class.java)
        }
    }

    private fun createViewModel() {
        mViewModel = NewsViewModel(
            mNewsManager = mNewsManager,
            mDispatchers = mCoroutinesTestRule.testDispatcherProvider
        )
    }
}