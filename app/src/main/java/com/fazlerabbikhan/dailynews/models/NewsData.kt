package com.fazlerabbikhan.dailynews.models

data class NewsData(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
)