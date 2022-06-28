package com.dev.sungho.news.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.dev.sungho.news.R
import com.dev.sungho.news.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private val mArguments: DetailFragmentArgs by navArgs()
    private var mBinding: FragmentDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDetailBinding.inflate(inflater, container, false)
        .apply { mBinding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        mBinding?.run {
            webView.settings.javaScriptEnabled = true
            webView.settings.setSupportZoom(false)

            /**
             * WebViewClient has unnecessary callback methods,
             * so created custom webview client only for [onReceivedError] event.
             */
            webView.webViewClient = CustomWebViewClient {
                showError(it)
            }
            webView.loadUrl(mArguments.url)
        }
    }

    private fun showError(message: String? = null) {
        context?.let {
            val errorMessage = message ?: resources.getString(R.string.webview_error)
            Toast.makeText(it, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}