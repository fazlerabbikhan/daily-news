package com.fazlerabbikhan.dailynews.global

import android.view.View
import com.fazlerabbikhan.dailynews.database.NewsArticle

class Global {
    companion object {
        var category: String? = null
        var newsArticle: NewsArticle? = null
        var contextView: View? = null
    }
}