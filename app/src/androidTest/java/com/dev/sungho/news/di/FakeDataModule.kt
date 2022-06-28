package com.dev.sungho.news.di

import com.dev.sungho.data.di.DataModule
import com.dev.sungho.data.repository.RemoteRepository
import com.dev.sungho.news.FakeRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
@Module
class FakeDataModule {
    @Provides
    @Singleton
    fun provideFakeRemoteRepository(): RemoteRepository = FakeRemoteRepository()
}