package com.dev.sungho.news.presentation.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.sungho.news.R
import com.dev.sungho.news.databinding.FragmentNewsBinding
import com.dev.sungho.news.foundation.BaseFragment
import com.dev.sungho.news.foundation.BaseViewModel
import com.dev.sungho.news.presentation.adapter.NewsItemAdapter
import com.dev.sungho.news.presentation.event.NewsEvent
import com.dev.sungho.news.presentation.event.NewsIntention
import com.dev.sungho.news.presentation.event.NewsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment :
    BaseFragment<NewsState, NewsEvent, NewsIntention, BaseViewModel<NewsState, NewsEvent, NewsIntention>>() {

    private var mBinding: FragmentNewsBinding? = null

    override val mViewModel: NewsViewModel by viewModels()

    /**
     * Fields
     */
    private var mNewsAdapter = NewsItemAdapter()

    /**
     * Application Life Cycle
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsBinding.inflate(inflater, container, false)
        .apply { mBinding = this }.root

    override fun handleViewState(viewState: NewsState) {
        when (viewState) {
            is NewsState.Loading -> showProgressDlg()
            is NewsState.Success -> {
                mNewsAdapter.submitList(viewState.result)
                showPage()
                hideProgressDlg()
            }
            is NewsState.Error -> {
                hideProgressDlg()
                showError(viewState.message)
            }
        }
    }

    override fun handleEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.HidePage -> hidePage()
        }
    }

    /**
     * View initialization and some logic should be from here not from onCreateView()
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    /**
     * View Initialization
     */
    private fun initRecyclerView() {
        mBinding?.run {
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = mNewsAdapter
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = true
                mViewModel.onIntention(NewsIntention.RefreshClicked)
            }
        }
    }

    /**
     * Interaction
     */
    private fun showProgressDlg() {
        mBinding?.swipeRefresh?.isRefreshing = true
    }

    private fun hideProgressDlg() {
        mBinding?.swipeRefresh?.isRefreshing = false
    }

    private fun showPage() {
        mBinding?.recyclerView?.visibility = VISIBLE
    }

    private fun hidePage() {
        mBinding?.recyclerView?.visibility = INVISIBLE
    }

    private fun showError(message: String? = null) {
        context?.let { ctx ->
            message?.let { Log.d(TAG, it) }
            Toast.makeText(ctx, resources.getString(R.string.error_text), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private val TAG: String? = NewsFragment::class.java.canonicalName
    }
}