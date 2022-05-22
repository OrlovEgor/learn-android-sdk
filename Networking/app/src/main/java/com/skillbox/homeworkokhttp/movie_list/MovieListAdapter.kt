package com.skillbox.homeworkokhttp.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.homeworkokhttp.R
import com.skillbox.homeworkokhttp.databinding.ItemMovieBinding

class MovieListAdapter() :
    ListAdapter<RemoteMovie, MovieListAdapter.Holder>(MovieListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieListDiffUtilCallback : DiffUtil.ItemCallback<RemoteMovie>() {
        override fun areItemsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem == newItem
        }

    }

    class Holder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RemoteMovie) {
            with(binding) {
                tittleItemTextView.text = item.tittle
                yearItemTextView.text = item.year
                genreItemTextView.text = item.genre
                Glide.with(itemView)
                    .load(item.poster)
                    .error(R.drawable.ic_error)
                    .into(posterImageView)
            }
        }

    }
}