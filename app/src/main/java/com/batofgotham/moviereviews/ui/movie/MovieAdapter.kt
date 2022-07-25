package com.batofgotham.moviereviews.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.batofgotham.moviereviews.R
import com.batofgotham.moviereviews.data.model.MovieEntity
import com.batofgotham.moviereviews.data.model.MovieRemote
import com.batofgotham.moviereviews.databinding.LayoutMovieItemBinding
import com.batofgotham.moviereviews.utils.BottomDialogInterface
import com.bumptech.glide.Glide

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200"

class MovieAdapter(private val bottomDialogInterface: BottomDialogInterface) : PagingDataAdapter<MovieEntity, MovieViewHolder>(
    DiffCallback
) {

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

    companion object DiffCallback: DiffUtil.ItemCallback<MovieEntity>(){
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }
    }
}


class MovieViewHolder(private val binding: LayoutMovieItemBinding, private val bottomDialogInterface: BottomDialogInterface): RecyclerView.ViewHolder(binding.root){

    fun bind(movie: MovieEntity?){

        binding.movieContainerView.setOnClickListener {
            bottomDialogInterface.onMovieSelected(movie)
        }

        binding.titleTextView.text = movie?.originalTitle

        val imageView = binding.posterImageView

        val posterUrl = IMAGE_BASE_URL + movie?.posterPath


        Glide.with(binding.root)
            .load(posterUrl)
            .placeholder(R.drawable.placeholder_image_36)
            .into(imageView)
    }
}