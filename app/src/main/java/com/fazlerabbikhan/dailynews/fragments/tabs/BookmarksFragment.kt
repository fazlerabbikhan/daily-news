package com.fazlerabbikhan.dailynews.fragments.tabs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fazlerabbikhan.dailynews.R
import com.fazlerabbikhan.dailynews.adapters.CardAdapter
import com.fazlerabbikhan.dailynews.databinding.FragmentBookmarksBinding
import com.fazlerabbikhan.dailynews.viewmodel.NewsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        //        Bottom navigation show
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        viewModel.loadBookmarkNews()

        val recycler = binding.cardNewsRecycler
        recycler.setHasFixedSize(true)
        viewModel.readNews.observe(viewLifecycleOwner) {
//            Log.d("TAG", "onResume:  ${it.size}")
            recycler.adapter = CardAdapter(it, viewModel)
        }
    }
}