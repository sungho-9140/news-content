package com.dev.sungho.news.foundation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Placing two livedata variables called [ViewState] and [ViewEvent].
 * <p>
 * Observing ViewState means that app is receiving loading, data fetched or error state.
 * Observing ViewEvent means that button, navigation events received.
 * Use [ViewIntention] when view want to talk with ViewModel.
 */
abstract class BaseViewModel<VS : ViewState, E : Event, I : Intention>(defaultViewState: VS) :
    ViewModel() {
    private val mViewState = MutableStateFlow(defaultViewState)

    /**
     * Current [ViewState]
     */
    val viewState: StateFlow<VS>
        get() = mViewState

    private val mEvent = Channel<E>(Channel.BUFFERED)

    /**
     * [Event]s that we don't want as part of viewState (eg. a navigation event)
     */
    val events = mEvent.receiveAsFlow()

    /**
     * Sets new [ViewState] to [viewState]
     */
    protected fun updateViewState(newState: VS) {
        mViewState.update { newState }
    }

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            mEvent.send(event)
        }
    }

    /**
     * Intention from view
     */
    abstract fun onIntention(intention: I)
}