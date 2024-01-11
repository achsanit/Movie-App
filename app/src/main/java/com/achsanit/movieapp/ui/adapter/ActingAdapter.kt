package com.achsanit.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.achsanit.movieapp.R
import com.achsanit.movieapp.data.entity.ActingEntity
import com.achsanit.movieapp.databinding.ItemActingBinding

class ActingAdapter : RecyclerView.Adapter<ActingAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemActingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ActingEntity) {
            with(binding) {
                tvYear.text = data.year
                tvTitleMovie.text = data.titleMovie
                tvAsCharacter.text = this@ViewHolder.itemView.context.getString(
                    R.string.text_as_character_placeholder,
                    data.character
                )
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<ActingEntity>() {
        override fun areItemsTheSame(oldItem: ActingEntity, newItem: ActingEntity): Boolean {
            return oldItem.titleMovie == newItem.titleMovie
        }

        override fun areContentsTheSame(oldItem: ActingEntity, newItem: ActingEntity): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun setData(data: List<ActingEntity>) = differ.submitList(data)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemActingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }
}