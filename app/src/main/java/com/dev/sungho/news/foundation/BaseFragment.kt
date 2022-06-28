package com.dev.sungho.news.foundation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment<VS : ViewState, E : Event, I : Intention, VM : BaseViewModel<VS, E, I>> :
    Fragment() {
    protected abstract val mViewModel: VM

    /**
     * Placing event collection logic in base class of fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mViewModel.viewState.collect { viewState ->
                        handleViewState(viewState)
                    }
                }

                launch {
                    mViewModel.events.collect { event ->
                        handleEvent(event)
                    }
                }
            }
        }
    }

    abstract fun handleViewState(viewState: VS)
    abstract fun handleEvent(event: E)
}