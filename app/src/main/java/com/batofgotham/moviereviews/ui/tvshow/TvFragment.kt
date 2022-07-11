package com.batofgotham.moviereviews.ui.tvshow

import android.os.Bundle
import android.util.Log
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
import com.batofgotham.moviereviews.utils.BottomDialogInterfaceTv
import com.batofgotham.moviereviews.utils.Utils
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200"

@AndroidEntryPoint
class TvFragment : Fragment(), BottomDialogInterfaceTv {

    private var _binding: FragmentTvBinding? = null

    private val binding: FragmentTvBinding
        get() = _binding!!

    private val viewModel: TvViewModel by viewModels()

    private lateinit var viewReference: View

    private lateinit var adapter: TvShowsAdapter

    private lateinit var detailBottomSheet: ViewGroup
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ViewGroup>

    private val TAG = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDetailBottomSheet()

        setUpRecyclerView()

        viewReference = view

        setupSearchView()

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

    private fun setupDetailBottomSheet() {

        detailBottomSheet = binding.detailBottomSheetContainer

        bottomSheetBehavior = BottomSheetBehavior.from(detailBottomSheet)

        Log.i(TAG, bottomSheetBehavior.state.toString())

        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)

        hideBottomSheet()
    }

    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            Log.i(TAG, newState.toString())
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            Log.i(TAG, "Sliding...")
        }
    }

    private fun showBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun send(tvShow: TvShow?) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        loadBottomDialog(tvShow)
    }

    private fun loadBottomDialog(tvShow: TvShow?) {
        tvShow?.apply {
            binding.titleTextView.text = name
            binding.posterImageView.setImageResource(R.drawable.ic_launcher_background)
            binding.releaseYearTextView.text =
                Utils.getYear(Utils.getDate(first_air_date)).toString()
            binding.overviewTextView.text = overview
            binding.ivOpen.setOnClickListener {
                Navigation.findNavController(viewReference)
                    .navigate(R.id.action_homeFragment_to_detailScreenFragment)
            }
        }

        val posterUrl = IMAGE_BASE_URL + tvShow?.poster_path

        Glide.with(requireContext())
            .load(posterUrl)
            .into(binding.posterImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}