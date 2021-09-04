package com.clicks.newsapp.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("totalResults")
    val totalResults: Int? = 0,
    @SerializedName("articles")
    val articles: List<Article>? = listOf()
) : Parcelable {
    @Parcelize
    data class Article(
        @SerializedName("source")
        val source: Source? = Source(),
        @SerializedName("author")
        val author: String? = "",
        @SerializedName("title")
        val title: String? = "",
        @SerializedName("description")
        val description: String? = "",
        @SerializedName("url")
        val url: String? = "",
        @SerializedName("urlToImage")
        val urlToImage: String? = "",
        @SerializedName("publishedAt")
        val publishedAt: String? = "",

    ) : Parcelable {
        @Parcelize

        data class Source(
            @SerializedName("id")
            val id: String? = "",
            @SerializedName("name")
            val name: String? = ""
        ) : Parcelable
    }
}