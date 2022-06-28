package com.dev.sungho.news.presentation.article

import androidx.lifecycle.viewModelScope
import com.dev.sungho.business.model.DomainResult
import com.dev.sungho.business.model.NewsItem
import com.dev.sungho.business.usecases.NewsManager
import com.dev.sungho.news.foundation.BaseViewModel
import com.dev.sungho.news.foundation.DispatcherProvider
import com.dev.sungho.news.presentation.event.NewsEvent
import com.dev.sungho.news.presentation.event.NewsIntention
import com.dev.sungho.news.presentation.event.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val mNewsManager: NewsManager,
    private val mDispatchers: DispatcherProvider
) : BaseViewModel<NewsState, NewsEvent, NewsIntention>(NewsState.Loading) {

    init {
        loadNewsList()
    }

    /**
     * Methods
     */
    override fun onIntention(intention: NewsIntention) {
        when (intention) {
            is NewsIntention.RefreshClicked -> {
                sendEvent(NewsEvent.HidePage)
                loadNewsList()
            }
        }
    }

    private fun loadNewsList() {
        viewModelScope.launch {
            try {
                val result = withContext(mDispatchers.io()) {
                    mNewsManager.getNewsArticles()
                }

                when (result) {
                    is DomainResult.Success<List<NewsItem>> -> handleNewsResultSuccess(result)
                    is DomainResult.Failure<List<NewsItem>> -> handleNewsResultFailure(result)
                }
            } catch (e: Exception) {
                e.message?.let { message ->
                    updateViewState(NewsState.Error(message))
                }
            }
        }
    }

    /**
     * Just alternative way using flow like stream way.
     * This method can be used instead of `loadNewsList()`
     */
    @SuppressWarnings("unused")
    private fun loadNewsListAlternative() {
        flow {
            emit(mNewsManager.getNewsArticles())
        }.flowOn(
            mDispatchers.io()
        ).onEach { result ->
            when (result) {
                is DomainResult.Success<List<NewsItem>> -> handleNewsResultSuccess(result)
                is DomainResult.Failure<List<NewsItem>> -> handleNewsResultFailure(result)
            }
        }.catch {
            updateViewState(NewsState.Error(it.message))
        }.launchIn(viewModelScope)
    }

    private fun handleNewsResultSuccess(
        response: DomainResult.Success<List<NewsItem>>
    ) {
        updateViewState(NewsState.Success(response.item))
    }

    private fun handleNewsResultFailure(
        result: DomainResult.Failure<List<NewsItem>>
    ) {
        when (result) {
            is DomainResult.Exception -> updateViewState(NewsState.Error(result.message))
            is DomainResult.UnknownFailure -> updateViewState(NewsState.Error("Unknown Error"))
        }
    }
}