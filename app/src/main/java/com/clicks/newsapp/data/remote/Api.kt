package com.clicks.newsapp.data.remote

import com.clicks.newsapp.data.model.NewsResponse
import com.clicks.newsapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(EndPoints.TOP_HEADLINE)
    suspend fun getNews(
        @Query("country") country : String =Constants.COUNTRY,
        @Query("apiKey") apiKey : String = Constants.API_KEY
    ): NewsResponse
}