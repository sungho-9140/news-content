package com.dev.sungho.data.di

import com.dev.sungho.data.network.APIService
import com.dev.sungho.data.repository.RemoteRepository
import com.dev.sungho.data.repository.RemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideRemoteRepository(service: APIService): RemoteRepository =
        RemoteRepositoryImpl(service)
}
