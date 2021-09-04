package com.clicks.newsapp.data.repository

import com.clicks.newsapp.data.remote.Api

class NewsRepository(private val api: Api) {
    suspend fun getNews() = api.getNews()

}