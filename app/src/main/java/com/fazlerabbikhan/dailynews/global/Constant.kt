package com.fazlerabbikhan.dailynews.global

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.fazlerabbikhan.dailynews.models.NewsArticle

class Constant {
    companion object {
        const val databaseName = "news_database"
        const val BASE_URL = "https://newsapi.org/v2/"
        const val TOKEN = "b6ab09b152ff42b4983e0b022af63088"

        var category: String = Category.BUSINESS
        var newsArticle: NewsArticle? = null

        @SuppressLint("StaticFieldLeak") var contextView: View? = null
        @SuppressLint("StaticFieldLeak") var context: Context? = null
    }
}