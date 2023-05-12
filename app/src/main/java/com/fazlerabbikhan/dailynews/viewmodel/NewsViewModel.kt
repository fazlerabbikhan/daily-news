package com.fazlerabbikhan.dailynews.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fazlerabbikhan.dailynews.database.NewsDatabase
import com.fazlerabbikhan.dailynews.global.Category
import com.fazlerabbikhan.dailynews.global.Constant
import com.fazlerabbikhan.dailynews.models.NewsArticle
import com.fazlerabbikhan.dailynews.network.NewsApi
import com.fazlerabbikhan.dailynews.repository.NewsRepository
import com.fazlerabbikhan.dailynews.utils.Internet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel (application: Application) : AndroidViewModel(application) {
    private val result = mutableListOf<NewsArticle>()
    private val repository: NewsRepository
    lateinit var readNews: LiveData<List<NewsArticle>>

    init {
        val newsDao = NewsDatabase.getDatabase(application).getNewsDao()
        repository = NewsRepository(newsDao)
        readNewsFromLocal()
    }

    fun readNewsFromLocal() {
        with(repository) {
            readNews = when (Constant.category) {
                Category.BUSINESS -> readNews(Category.BUSINESS)
                Category.ENTERTAINMENT -> readNews(Category.ENTERTAINMENT)
                Category.GENERAL -> readNews(Category.GENERAL)
                Category.HEALTH -> readNews(Category.HEALTH)
                Category.SCIENCE -> readNews(Category.SCIENCE)
                Category.SPORTS -> readNews(Category.SPORTS)
                else -> readNews(Category.TECHNOLOGY)
            }
        }
    }

    fun getNewsFromRemote() {
        if (Internet.isOnline()) {
            viewModelScope.launch {
                try {
                    val response = NewsApi.retrofitService.topHeadlinesNews(Constant.category, Constant.TOKEN)
                    response.articles?.map {
                        result.add(
                            NewsArticle(
                                0,
                                it.title,
                                it.author,
                                it.content,
                                it.description,
                                it.publishedAt,
                                it.source?.name,
                                it.url,
                                it.urlToImage,
                                Constant.category
                            )
                        )
                    }
                    addNews()
//                Log.d("TAG", "getTopHeadlines: called ${response.articles.size}")
                } catch (e: Exception) {
                    Log.d("TAG", "$e")
                }
            }
        }
    }

    private fun addNews() {
        Log.d("TAG", "addNews: result${result.size}")
        for (i in result) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addNews(i)
            }
        }
        result.clear()
        Log.d("TAG", "addNews: result${result.size}")
    }

    fun addBookmarkNews(newsArticle: NewsArticle) {
        Log.d("TAG", "addBookmarkNews: ${newsArticle.id}")
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBookmarkNews(
                newsArticle.id, !newsArticle.isBookmark
            )
        }
    }

    fun loadBookmarkNews() {
        readNews = repository.getBookmarkNews()
    }

}