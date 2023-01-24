package com.fazlerabbikhan.dailynews.fragments.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fazlerabbikhan.dailynews.adapters.CategoryAdapter
import com.fazlerabbikhan.dailynews.adapters.CategoryAdapter.Companion.categoryList
import com.fazlerabbikhan.dailynews.databinding.FragmentHomeBinding
import com.fazlerabbikhan.dailynews.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this)[NewsViewModel::class.java]

        val tabLayout = binding.tabLayoutHome
        val viewPage = binding.viewPager2

        val categoryAdapter = CategoryAdapter(childFragmentManager, lifecycle)
        viewPage.adapter = categoryAdapter
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = categoryList[position].title
        }.attach()
    }
}