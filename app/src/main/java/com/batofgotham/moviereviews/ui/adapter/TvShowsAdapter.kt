package com.batofgotham.moviereviews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.batofgotham.moviereviews.data.model.TvShows
import com.batofgotham.moviereviews.databinding.LayoutTvShowsItemBinding
import com.bumptech.glide.Glide

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200"

class TvShowsAdapter(private val listener: TvShowsViewHolder.OnClickListener) :
    ListAdapter<TvShows, TvShowsViewHolder>(DiffCallback) {

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {
        return TvShowsViewHolder(
            LayoutTvShowsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        val item = currentList[position]
        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TvShows>() {
        override fun areItemsTheSame(oldItem: TvShows, newItem: TvShows): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShows, newItem: TvShows): Boolean {
            return oldItem == newItem
        }
    }
}

class TvShowsViewHolder(private val binding: LayoutTvShowsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tvShows: TvShows) {
        binding.titleTextView.text = tvShows.name
        val imageView = binding.posterImageView
        val context = binding.root.context

        val posterUrl = IMAGE_BASE_URL + tvShows.poster_path

        Glide.with(binding.root)
            .load(posterUrl)
            .into(imageView)
    }

    interface OnClickListener {
        fun onClick(tvShows: TvShows)
    }

}