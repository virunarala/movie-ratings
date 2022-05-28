package com.batofgotham.moviereviews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.batofgotham.moviereviews.data.model.Movie
import com.batofgotham.moviereviews.databinding.LayoutMovieItemBinding

class MovieAdapter(): ListAdapter<Movie,MovieViewHolder>(DiffCallback) {

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutMovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = currentList[position]
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


class MovieViewHolder(private val binding: LayoutMovieItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(movie: Movie){
        //(TODO) Use Glide to load images
        binding.posterImageView.text = movie.posterPath
        binding.titleTextView.text = movie.originalTitle
    }
}