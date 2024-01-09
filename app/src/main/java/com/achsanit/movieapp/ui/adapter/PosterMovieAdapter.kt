package com.achsanit.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.achsanit.movieapp.BuildConfig
import com.achsanit.movieapp.data.entity.MoviePoster
import com.achsanit.movieapp.databinding.ItemMovieBinding
import com.achsanit.movieapp.utils.setShimmerPlaceholder

class PosterMovieAdapter(private val onItemClick: (MoviePoster) -> Unit):
    RecyclerView.Adapter<PosterMovieAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MoviePoster) {
            with(binding) {
                val imageUrl = "${BuildConfig.BASE_IMAGE_URL}${data.posterPath}"
                ivPoster.load(imageUrl) { setShimmerPlaceholder() }
                tvRating.text = String.format("%.1f", data.rating)
                root.setOnClickListener { onItemClick(data) }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<MoviePoster>() {
        override fun areItemsTheSame(oldItem: MoviePoster, newItem: MoviePoster): Boolean {
            return oldItem.movieId == newItem.movieId
        }

        override fun areContentsTheSame(oldItem: MoviePoster, newItem: MoviePoster): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun setData(data: List<MoviePoster>) = differ.submitList(data)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }
}