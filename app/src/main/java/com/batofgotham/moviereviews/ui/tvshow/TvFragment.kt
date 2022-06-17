package com.batofgotham.moviereviews.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.batofgotham.moviereviews.R
import com.batofgotham.moviereviews.data.model.TvShow
import com.batofgotham.moviereviews.databinding.FragmentTvBinding
import com.batofgotham.moviereviews.ui.adapter.TvShowsAdapter
import com.batofgotham.moviereviews.ui.adapter.TvShowsViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvFragment : Fragment(), TvShowsViewHolder.OnClickListener {

    private var _binding: FragmentTvBinding? = null

    private val binding: FragmentTvBinding
        get() = _binding!!

    private val viewModel: TvViewModel by viewModels()

    private lateinit var viewReference: View

    private lateinit var adapter: TvShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        viewReference = view

        setupSearchView()

        binding.fabFilter.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_choiceChipsFragment)
        }

        viewModel.tvShow.observe(viewLifecycleOwner) {
            if (it != null)
                adapter.submitData(lifecycle, it)
        }

        viewModel.searchTvShows.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitData(lifecycle, it)
            }
        }


    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.rvTvShows
        val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
        gridLayoutManager.spanCount = 3

        adapter = TvShowsAdapter(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun setupSearchView() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.search(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(tvShow: TvShow) {
        Navigation.findNavController(viewReference)
            .navigate(R.id.action_homeFragment_to_detailScreenFragment)
    }

}