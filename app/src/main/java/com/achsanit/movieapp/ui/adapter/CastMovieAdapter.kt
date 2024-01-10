package com.achsanit.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.achsanit.movieapp.BuildConfig
import com.achsanit.movieapp.R
import com.achsanit.movieapp.data.entity.CastMovieEntity
import com.achsanit.movieapp.databinding.ItemCastBinding
import com.achsanit.movieapp.utils.setShimmerPlaceholder

class CastMovieAdapter: RecyclerView.Adapter<CastMovieAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemCastBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CastMovieEntity) {
            with(binding) {
                tvActorName.text = data.name
                tvCharacterName.text = data.character

                if (data.profilePath.isNotBlank()) {
                    ivActor.load("${BuildConfig.BASE_IMAGE_URL}${data.profilePath}") {
                        setShimmerPlaceholder()
                    }
                } else {
                    ivActor.load(R.drawable.bg_broken_image)
                }

            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CastMovieEntity>() {
        override fun areItemsTheSame(oldItem: CastMovieEntity, newItem: CastMovieEntity): Boolean {
            return oldItem.idCast == newItem.idCast
        }

        override fun areContentsTheSame(oldItem: CastMovieEntity, newItem: CastMovieEntity): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun setData(data: List<CastMovieEntity>) = differ.submitList(data)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }
}