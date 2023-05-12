package com.fazlerabbikhan.dailynews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fazlerabbikhan.dailynews.global.Constant
import com.fazlerabbikhan.dailynews.models.NewsArticle

@Database(entities = [NewsArticle::class], version = 8, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null
        fun getDatabase(context: Context): NewsDatabase {
            val tempInstance = INSTANCE

            if(tempInstance!=null){
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    Constant.databaseName
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
