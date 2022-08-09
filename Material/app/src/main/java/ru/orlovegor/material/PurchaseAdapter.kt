package ru.orlovegor.material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovegor.material.data.Purchase
import ru.orlovegor.material.databinding.ItemPurchaseBinding

class PurchaseAdapter : ListAdapter<Purchase, PurchaseAdapter.Holder>(PurchaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemPurchaseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class PurchaseDiffUtil : DiffUtil.ItemCallback<Purchase>() {
        override fun areItemsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
            return oldItem == newItem
        }

    }

    class Holder(
        private val binding: ItemPurchaseBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Purchase) {
        }


    }
}