package com.fazlerabbikhan.dailynews.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fazlerabbikhan.dailynews.fragments.categories.*
import com.fazlerabbikhan.dailynews.models.Category

class CategoryAdapter(manager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(manager, lifecycle){
    companion object {
        val categoryList = listOf(
            Category(BusinessFragment(), "Business"),
            Category(EntertainmentFragment(), "Entertainment"),
            Category(GeneralFragment(), "General"),
            Category(HealthFragment(), "Health"),
            Category(ScienceFragment(), "Science"),
            Category(SportsFragment(), "Sports"),
            Category(TechnologyFragment(), "Technology")
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun createFragment(position: Int): Fragment {
        return categoryList[position].fragment
    }
}