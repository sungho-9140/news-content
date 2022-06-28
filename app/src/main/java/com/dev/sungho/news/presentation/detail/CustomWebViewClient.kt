package com.dev.sungho.news.presentation.detail

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient(
    private val mCallback: (String) -> Unit
) : WebViewClient() {
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        mCallback.invoke(error?.description?.toString().orEmpty())
    }
}