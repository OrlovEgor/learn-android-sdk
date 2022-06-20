package ru.orlovegor.moviesearchapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.orlovegor.moviesearchapp.R
import ru.orlovegor.moviesearchapp.data.RemoteMovie
import ru.orlovegor.moviesearchapp.databinding.ItemMovieBinding

class MovieAdapter : ListAdapter<RemoteMovie, MovieAdapter.Holder>(MovieDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.Holder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: MovieAdapter.Holder, position: Int) {
        holder.bind(getItem(position))
    }


    class MovieDiffUtil : DiffUtil.ItemCallback<RemoteMovie>() {
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
                itemMovieTittleTextview.text = item.title
                Glide.with(itemView)
                    .load(item.image)
                    .error(R.drawable.ic_error_24)
                    .into(itemMovieImageView)
            }
        }
    }

}
