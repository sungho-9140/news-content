package com.dev.sungho.news.foundation

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Providing injected dispatcher for unit test
 */
interface DispatcherProvider {
    fun main(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}