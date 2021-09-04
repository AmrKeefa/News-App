package com.clicks.newsapp.di

import com.clicks.newsapp.data.remote.Api
import com.clicks.newsapp.data.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun newsRepository(api: Api): NewsRepository {
        return NewsRepository(api)
    }
}