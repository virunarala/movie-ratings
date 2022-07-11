package com.batofgotham.moviereviews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.batofgotham.moviereviews.data.model.TvShow
import com.batofgotham.moviereviews.databinding.LayoutTvShowsItemBinding
import com.batofgotham.moviereviews.utils.BottomDialogInterfaceTv
import com.bumptech.glide.Glide

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200"

class TvShowsAdapter(private val bottomDialogInterface: BottomDialogInterfaceTv) :
    PagingDataAdapter<TvShow, TvShowsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {

        return TvShowsViewHolder(
            LayoutTvShowsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), bottomDialogInterface
        )
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null)
            holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TvShow>() {
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem == newItem
        }
    }
}

class TvShowsViewHolder(
    private val binding: LayoutTvShowsItemBinding,
    private val bottomDialogInterface: BottomDialogInterfaceTv
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tvShow: TvShow) {

        binding.tvContainerView.setOnClickListener {
            bottomDialogInterface.send(tvShow)
        }

        binding.titleTextView.text = tvShow.name
        val imageView = binding.posterImageView
        val context = binding.root.context

        val posterUrl = IMAGE_BASE_URL + tvShow.poster_path

        Glide.with(binding.root)
            .load(posterUrl)
            .into(imageView)
    }

}