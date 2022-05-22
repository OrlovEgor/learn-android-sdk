package com.skillbox.homework19.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.homework19.DataClasses.Animal
import com.skillbox.homework19.databinding.ItemPetBinding

class PetAdapterDelegate(
    private val onLongItemClick: (position: Int) -> Unit,
    private val onItemClick: (id: Long, name: String) -> Unit
) :
    AbsListItemAdapterDelegate<Animal.Pet, Animal, PetAdapterDelegate.PetHolder>() {

    override fun isForViewType(item: Animal, items: MutableList<Animal>, position: Int): Boolean {
        return item is Animal.Pet
    }

    override fun onCreateViewHolder(parent: ViewGroup): PetHolder {
        val binding = ItemPetBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PetHolder(binding, onLongItemClick, onItemClick)
    }

    override fun onBindViewHolder(item: Animal.Pet, holder: PetHolder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class PetHolder(
        private val binding: ItemPetBinding,
        onLongItemClick: (position: Int) -> Unit,
        onItemClick: (id: Long, name: String) -> Unit
    ) : BaseAnimalHolder(binding.root, onLongItemClick, onItemClick) {

        fun bind(animal: Animal.Pet) {
            bindMainInfo(animal.id, animal.name, animal.age, animal.photoLink)
        }
    }
}