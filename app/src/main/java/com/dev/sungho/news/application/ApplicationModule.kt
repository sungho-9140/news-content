package com.dev.sungho.news.application

import com.dev.sungho.news.foundation.DispatcherProvider
import com.dev.sungho.news.foundation.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Below are globally injected object used to all over the application.
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider =
        DispatcherProviderImpl()
}