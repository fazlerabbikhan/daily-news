package com.fazlerabbikhan.dailynews.repository

import com.fazlerabbikhan.dailynews.database.NewsDao
import com.fazlerabbikhan.dailynews.models.NewsArticle

class NewsRepository(private val newsDao: NewsDao){

    fun readNews(category: String) = newsDao.readNews(category)

    suspend fun addNews(newsArticle: NewsArticle) {
        newsDao.addNews(newsArticle)
    }

    suspend fun addBookmarkNews(id: Int, isBookmark: Boolean) {
        newsDao.addBookmarkNews(id, isBookmark)
    }

    fun getBookmarkNews() = newsDao.getBookmarkNews()
}