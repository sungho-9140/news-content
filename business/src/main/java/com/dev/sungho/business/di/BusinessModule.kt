package com.dev.sungho.business.di

import com.dev.sungho.business.mapper.NewsResponseMapper
import com.dev.sungho.business.usecases.NewsManager
import com.dev.sungho.business.usecases.NewsManagerImpl
import com.dev.sungho.business.utils.MappingUtil
import com.dev.sungho.data.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BusinessModule {
    @Provides
    fun provideMappingUtil(): MappingUtil = MappingUtil()

    @Provides
    fun provideNewsManager(
        remoteRepository: RemoteRepository,
        mappingUtil: MappingUtil
    ): NewsManager = NewsManagerImpl(
        mRemoteRepository = remoteRepository,
        mNewsMapper = NewsResponseMapper(mappingUtil)
    )
}
