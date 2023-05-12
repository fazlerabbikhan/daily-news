package com.fazlerabbikhan.dailynews.network

import com.fazlerabbikhan.dailynews.global.Constant.Companion.BASE_URL
import com.fazlerabbikhan.dailynews.models.News
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object NewsApi {
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}

interface NewsApiService {
    @GET("top-headlines?country=us")
    suspend fun topHeadlinesNews(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): News
}