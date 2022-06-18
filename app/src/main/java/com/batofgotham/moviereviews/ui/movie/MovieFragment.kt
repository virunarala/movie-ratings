package com.batofgotham.moviereviews.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.batofgotham.moviereviews.R
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.databinding.FragmentMovieBinding
import com.batofgotham.moviereviews.ui.adapter.MovieAdapter
import com.batofgotham.moviereviews.utils.BottomDialogInterface
import com.batofgotham.moviereviews.utils.Utils
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200"

@AndroidEntryPoint
class MovieFragment : Fragment(), BottomDialogInterface {

    private val TAG = this.javaClass.simpleName

    private var _binding: FragmentMovieBinding? = null

    private val binding: FragmentMovieBinding
        get() = _binding!!

    private lateinit var detailBottomSheet: ViewGroup
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ViewGroup>

    private val viewModel: MovieViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupDetailBottomSheet()


        val recyclerView = binding.moviesRecyclerView
        val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
        gridLayoutManager.spanCount = 3

        val adapter = MovieAdapter(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.getMovies().asFlow().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupDetailBottomSheet(){

        detailBottomSheet = binding.detailBottomSheetContainer

        bottomSheetBehavior = BottomSheetBehavior.from(detailBottomSheet)

        Log.i(TAG,bottomSheetBehavior.state.toString())

        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)

        hideBottomSheet()
    }

    private val bottomSheetCallback = object: BottomSheetBehavior.BottomSheetCallback(){
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            Log.i(TAG,newState.toString())
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            Log.i(TAG,"Sliding...")
        }
    }

    private fun showBottomSheet(){
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun hideBottomSheet(){
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun send(movie: Movie?) {
        bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
        loadBottomDialog(movie)
    }

    private fun loadBottomDialog(movie: Movie?) {
        movie?.apply {
            binding.titleTextView.text = title
            binding.posterImageView.setImageResource(R.drawable.ic_launcher_background)
            binding.releaseYearTextView.text = Utils.getYear(Utils.getDate(releaseDate)).toString()
            binding.overviewTextView.text = overview
        }

        val posterUrl = IMAGE_BASE_URL + movie?.posterPath

        Glide.with(requireContext())
            .load(posterUrl)
            .into(binding.posterImageView)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}