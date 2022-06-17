package com.batofgotham.moviereviews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.databinding.LayoutMovieItemBinding
import com.batofgotham.moviereviews.utils.BottomDialogInterface
import com.bumptech.glide.Glide

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200"

class MovieAdapter(private val bottomDialogInterface: BottomDialogInterface) : PagingDataAdapter<Movie,MovieViewHolder>(DiffCallback) {

    /**
     * These variables are only being used for testing the image loading library.
     * They are to be replaced with the data from cached Configuration object.
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutMovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),bottomDialogInterface)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}


class MovieViewHolder(private val binding: LayoutMovieItemBinding, private val bottomDialogInterface: BottomDialogInterface): RecyclerView.ViewHolder(binding.root){

    fun bind(movie: Movie?){

        binding.movieContainerView.setOnClickListener {
            bottomDialogInterface.send(movie)
        }

        binding.titleTextView.text = movie?.originalTitle

        val imageView = binding.posterImageView

        val posterUrl = IMAGE_BASE_URL + movie?.posterPath

        //(TODO) Add a placeholder image for when the poster is not available and to show while loading a image
        Glide.with(binding.root)
            .load(posterUrl)
            .into(imageView)
    }
}