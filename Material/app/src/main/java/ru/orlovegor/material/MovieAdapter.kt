package ru.orlovegor.material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.orlovegor.material.data.RemoteMovie
import ru.orlovegor.material.databinding.ItemPurchaseBinding

class MovieAdapter : ListAdapter<RemoteMovie, MovieAdapter.Holder>(PurchaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemPurchaseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class PurchaseDiffUtil : DiffUtil.ItemCallback<RemoteMovie>() {
        override fun areItemsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem == newItem
        }

    }

    class Holder(
        private val binding: ItemPurchaseBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RemoteMovie) {
            with(binding){
                tittleTextView.text = item.title
                dateTextView.text = item.movieType.name.lowercase()
                Picasso.get().load(item.imageUrl).error(R.drawable.ic_error).into(imageView)
            }
        }
    }
}