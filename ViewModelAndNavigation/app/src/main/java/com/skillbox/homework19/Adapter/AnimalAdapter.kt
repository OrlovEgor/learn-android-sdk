package com.skillbox.homework19.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.homework19.DataClasses.Animal

class AnimalAdapter(
    private val onLongItemClick: (position: Int) -> Unit,
    private val onItemClick: (id: Long, name: String) -> Unit
) : AsyncListDifferDelegationAdapter<Animal>(AnimalDiffUtilCallback()) {
    init {
        delegatesManager.addDelegate(PetAdapterDelegate(onLongItemClick, onItemClick))
            .addDelegate(WildAdapterDelegate(onLongItemClick, onItemClick))
    }
}

class AnimalDiffUtilCallback : DiffUtil.ItemCallback<Animal>() {
    override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
        return when {
            oldItem is Animal.Pet && newItem is Animal.Pet -> oldItem.id == newItem.id
            oldItem is Animal.Wild && newItem is Animal.Wild -> oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
        return oldItem == newItem
    }
}
