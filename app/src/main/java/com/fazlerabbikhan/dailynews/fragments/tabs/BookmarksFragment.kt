package com.fazlerabbikhan.dailynews.fragments.tabs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.fazlerabbikhan.dailynews.R
import com.fazlerabbikhan.dailynews.adapters.CardAdapter
import com.fazlerabbikhan.dailynews.database.NewsArticle
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

        // Bottom navigation show
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE

        // Search menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchItem = menu.findItem(R.id.action_search)
                val searchView: SearchView = searchItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    android.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
//                        viewModel.searchNewsBookmark(p0 ?: "")
                        return false
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    override fun onQueryTextChange(msg: String): Boolean {
//                        filter data
                        val queryResult = mutableListOf<NewsArticle>()
                        viewModel.readNews.value?.map {
                            if (it.title?.contains(msg, ignoreCase = true) == true) {
                                queryResult.add(it)
                            }
                        }
                        binding.cardNewsRecycler.adapter = CardAdapter(queryResult, viewModel)
//                        Log.d("TAG", "onQueryTextChange: ${queryResult.size}")
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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

        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.getNewsFromRemote()
            recycler.adapter?.notifyDataSetChanged()
        }
    }
}