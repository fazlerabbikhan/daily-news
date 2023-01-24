package com.fazlerabbikhan.dailynews.fragments.categories

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fazlerabbikhan.dailynews.adapters.CardAdapter
import com.fazlerabbikhan.dailynews.databinding.FragmentTechnologyBinding
import com.fazlerabbikhan.dailynews.global.Global
import com.fazlerabbikhan.dailynews.viewmodel.NewsViewModel

class TechnologyFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private var _binding: FragmentTechnologyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTechnologyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        Global.category = "technology"
        viewModel.readNewsFromLocal()

        val recycler = binding.cardNewsRecycler
        recycler.setHasFixedSize(true)
        viewModel.readNews.observe(viewLifecycleOwner) {
//            Log.d("TAG", "onResume:  ${it.size}")
            if (it.isEmpty()) {
                viewModel.getNewsFromRemote()
                recycler.adapter?.notifyDataSetChanged()
            }
            recycler.adapter = CardAdapter(it, viewModel)
        }
    }
}