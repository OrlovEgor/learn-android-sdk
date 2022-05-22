package com.skillbox.homework19.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.homework19.DataClasses.Animal
import com.skillbox.homework19.R
import com.skillbox.homework19.databinding.ItemWildBinding

class WildAdapterDelegate(
    private val onLongItemClick: (position: Int) -> Unit,
    private val onItemClick: (id: Long, name: String) -> Unit
) :
    AbsListItemAdapterDelegate<Animal.Wild, Animal, WildAdapterDelegate.WildHolder>() {
    override fun isForViewType(item: Animal, items: MutableList<Animal>, position: Int): Boolean {
        return item is Animal.Wild
    }

    override fun onCreateViewHolder(parent: ViewGroup): WildAdapterDelegate.WildHolder {
        val binding = ItemWildBinding.inflate(
            LayoutInflater.from
                (parent.context), parent, false
        )
        return WildHolder(binding, onLongItemClick, onItemClick)
    }

    override fun onBindViewHolder(
        item: Animal.Wild,
        holder: WildAdapterDelegate.WildHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class WildHolder(
        private val binding: ItemWildBinding,
        onLongItemClick: (position: Int) -> Unit,
        onItemClick: (id: Long, name: String) -> Unit
    ) : BaseAnimalHolder(binding.root, onLongItemClick, onItemClick) {

        fun bind(animal: Animal.Wild) {
            bindMainInfo(animal.id, animal.name, animal.age, animal.photoLink)
            binding.wildAggressiveStatusTextView.text =
                itemView.context.resources.getString(R.string.aggressive_text) + animal.aggression
        }
    }
}