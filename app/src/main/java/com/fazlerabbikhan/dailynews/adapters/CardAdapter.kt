package com.fazlerabbikhan.dailynews.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fazlerabbikhan.dailynews.R
import com.fazlerabbikhan.dailynews.database.NewsArticle
import com.fazlerabbikhan.dailynews.global.Global
import com.fazlerabbikhan.dailynews.viewmodel.NewsViewModel

class CardAdapter(
    private val dataset: List<NewsArticle>,
    private val viewModel: NewsViewModel
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val newsTitle: TextView = view.findViewById(R.id.card_news_title)
        val newsDescription: TextView = view.findViewById(R.id.card_news_description)
        val newsBookmark: ImageView = view.findViewById(R.id.card_news_bookmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_card, parent, false)
        return CardViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = dataset[position]
        holder.newsTitle.text = item.title ?: "----"
        holder.newsDescription.text = item.description ?: "----"
        Glide
            .with(holder.itemView.context)
            .load(item.urlToImage)
            .centerCrop()
//            .thumbnail(
//                Glide.with(holder.itemView.context)
//                    .load(R.drawable.search_gif)
//            )
            .into(holder.itemView.findViewById(R.id.card_news_image));

//        details fragment action
//        holder.itemView.setOnClickListener {
////            Log.d("TAG", "onBindViewHolder: click")
//            Global.newsArticle = item
////            Log.d("TAG", "onBindViewHolder: ${Global.newsArticle}")
//            it.findNavController().navigate(R.id.newsArticleFragment)
//        }

//        Bookmark button
        holder.newsBookmark.setOnClickListener {
            Log.d("TAG", "onBindViewHolder: click")
            viewModel.addBookmarkNews(item)
        }

        if(item.isBookmark) {
            holder.newsBookmark.setImageResource(R.drawable.ic_launcher_bookmark_24)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}