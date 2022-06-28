package com.dev.sungho.data

import com.dev.sungho.data.model.NewsResponse
import com.dev.sungho.data.network.APIService
import com.dev.sungho.data.repository.RemoteRepository
import com.dev.sungho.data.repository.RemoteRepositoryImpl
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class RemoteRepositoryTest {
    @get:Rule
    var mCoroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var mApiService: APIService

    private lateinit var mRemoteRepository: RemoteRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        mRemoteRepository = RemoteRepositoryImpl(mApiService)
    }

    /**
     * Assume api response is successful and then verifying if each fields are matched with expectation.
     */
    @Test
    fun `Given API request When API response returned successfully Then response must have data`() {
        mCoroutinesTestRule.testDispatcher.runBlockingTest {
            val response = Response.success(TestUtils.getNewsResponse())
            whenever(mApiService.getNewsArticles()).thenReturn(response)

            val result = mRemoteRepository.getNewsArticles()
            val value = result.getOrNull()

            assertThat(result.isSuccess).isTrue
            assertThat(value).isNotNull

            value?.run {
                assertThat(id).isEqualTo(67184313L)
                assertThat(displayName).isEqualTo("AFR iPad Editor's Choice")
                assertThat(timeStamp).isEqualTo(1635818972904L)

                assets?.get(0)?.run {
                    assertThat(id).isEqualTo(1520479143L)
                    assertThat(url).isEqualTo("http://www.afr.com/news/politics/national/dancing-is-back-what-you-can-do-in-nsw-from-monday-20211101-p5957r")
                    assertThat(headline).isEqualTo("Dancing is back: what you can do in NSW from Monday")
                    assertThat(theAbstract).isEqualTo("Fully vaccinated Sydneysiders will have most of their freedoms back from Monday, November 8 after the state government brought forward its reopening road map")
                    assertThat(byLine).isEqualTo("")
                    assertThat(timeStamp).isEqualTo(1635813193000L)

                    relatedImages?.get(0)?.run {
                        assertThat(id).isEqualTo(1030138713L)
                        assertThat(url).isEqualTo("https://www.fairfaxstatic.com.au/content/dam/images/h/1/b/g/4/9/image.related.wideLandscape.1500x844.p5957r.13zzqx.png/1635818974887.jpg")
                        assertThat(width).isEqualTo(1500)
                        assertThat(height).isEqualTo(844)
                    } ?: throw NullPointerException(ERROR_MESSAGE)
                } ?: throw NullPointerException(ERROR_MESSAGE)
            } ?: throw NullPointerException(ERROR_MESSAGE)
        }
    }

    /**
     * Assume api response return an error, like 400 or 500 error,
     * then verifying if it has error message and matched.
     */
    @Test
    fun `Given API request When API response encounter error with code Then response fails with error`() {
        mCoroutinesTestRule.testDispatcher.runBlockingTest {
            val response = Response.error<NewsResponse>(500, mockResponseBody())
            whenever(mApiService.getNewsArticles()).thenReturn(response)

            val result = mRemoteRepository.getNewsArticles()

            assertThat(result.isSuccess).isFalse
            assertThat(result.exceptionOrNull()?.message)
                .isEqualTo("Server error: code:500, message:Response.error()")
        }
    }

    /**
     * Assume api response return empty body then verifying if it has error message and matched.
     */
    @Test
    fun `Given API request When API response returns null body Then response fails with error`() {
        mCoroutinesTestRule.testDispatcher.runBlockingTest {
            val response = Response.success(TestUtils.getEmptyNewsResponse())
            whenever(mApiService.getNewsArticles()).thenReturn(response)

            val result = mRemoteRepository.getNewsArticles()

            assertThat(result.isSuccess).isFalse
            assertThat(result.exceptionOrNull()?.message)
                .isEqualTo("Server error: code:200, message:OK")
        }
    }

    private fun mockResponseBody(): ResponseBody =
        "".toResponseBody(null)

    companion object {
        private const val ERROR_MESSAGE = "Item should not be null!!"
    }
}