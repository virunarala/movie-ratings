package com.batofgotham.moviereviews.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.batofgotham.moviereviews.R
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.databinding.FragmentDetailScreenBinding
import com.batofgotham.moviereviews.databinding.FragmentMovieBinding
import com.batofgotham.moviereviews.ui.adapter.MovieAdapter
import com.batofgotham.moviereviews.ui.detail.DetailScreenFragment
import com.batofgotham.moviereviews.utils.BottomDialogInterface
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment(), BottomDialogInterface {

    private val TAG = this.javaClass.simpleName

    private var _binding: FragmentMovieBinding? = null

    private val binding: FragmentMovieBinding
        get() = _binding!!

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

        if(activity==null)
            Log.i(TAG,"Activity is null")

        val detailFragment = binding.detailBottomSheetContainer

//        binding.titleTextView.text = "Doctor Strange: Multiverse of Madness"
//        binding.posterImageView.setImageResource(R.drawable.ic_launcher_background)
//        binding.releaseYearTextView.text = "2022"

        val bottomSheetBehavior = BottomSheetBehavior.from(detailFragment)

        Log.i(TAG,bottomSheetBehavior.state.toString())

        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
    }

    private val bottomSheetCallback = object: BottomSheetBehavior.BottomSheetCallback(){
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            Log.i(TAG,newState.toString())
            when(newState){
                BottomSheetBehavior.STATE_EXPANDED -> binding.overviewTextView.visibility = View.VISIBLE
                BottomSheetBehavior.STATE_COLLAPSED -> binding.overviewTextView.visibility = View.GONE
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            Log.i(TAG,"Sliding...")
        }
    }

    override fun send(movie: Movie?) {
        loadBottomDialog(movie)
    }

    private fun loadBottomDialog(movie: Movie?) {
        movie?.apply {
            binding.titleTextView.text = title
            binding.posterImageView.setImageResource(R.drawable.ic_launcher_background)
            binding.releaseYearTextView.text = releaseDate
            binding.overviewTextView.text = overview
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}