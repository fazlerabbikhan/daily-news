package com.fazlerabbikhan.dailynews.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNews(newsArticle: NewsArticle)

    @Query("SELECT * FROM news WHERE category=:category ORDER BY id DESC")
    fun readNews(category: String): LiveData<List<NewsArticle>>

//    // Search function
//    @Query("SELECT * FROM news WHERE title LIKE '%' || :search || '%'")
//    fun searchNews(search: String?): LiveData<List<NewsArticle>>

    @Query("UPDATE news SET isBookmark=:isBookmark WHERE id=:id")
    suspend fun addBookmarkNews(id: Int, isBookmark: Boolean)

    @Query("SELECT * FROM news WHERE isBookmark=1 ORDER BY id DESC")
    fun getBookmarkNews(): LiveData<List<NewsArticle>>
}