package com.fazlerabbikhan.dailynews.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.fazlerabbikhan.dailynews.database.NewsArticle
import com.fazlerabbikhan.dailynews.database.NewsDatabase
import com.fazlerabbikhan.dailynews.global.Global
import com.fazlerabbikhan.dailynews.models.Article
import com.fazlerabbikhan.dailynews.models.NewsData
import com.fazlerabbikhan.dailynews.network.NewsApi
import com.fazlerabbikhan.dailynews.repository.NewsRepository
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class NewsViewModel (application: Application) : AndroidViewModel(application)
{
//    private val _news = MutableLiveData<NewsData>()
//    private val _articles = MutableLiveData<List<Article>?>()
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
            Log.d("TAG", "readNewsFromLocal: ${Global.category}")
            readNews = when (Global.category) {
                "business" -> readNews("business")
                "entertainment" -> readNews("entertainment")
                "general" -> readNews("general")
                "health" -> readNews("health")
                "science" -> readNews("science")
                "sports" -> readNews("sports")
                else -> readNews("technology")
            }
        }
    }

    fun getNewsFromRemote() {
            Log.d("TAG", "getNewsFromRemote: call news api")
            viewModelScope.launch {
                try {
                    val response = NewsApi.retrofitService.topHeadlinesNews(
                        Global.category!!
                    )
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
                                Global.category
                            )
                        )
                    }
                    addNews()
                Log.d("TAG", "getTopHeadlines: called ${response.articles?.size}")
                } catch (e: Exception) {
                    Log.d("TAG", "$e")
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