package ru.orlovegor.contentprovider.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovegor.contentprovider.data.Contact
import ru.orlovegor.contentprovider.databinding.ItemContactBinding

class ContactsAdapter(private val onContactClick: (Contact) -> Unit) :
    ListAdapter<Contact, ContactsAdapter.ContactHolder>(ContactDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactHolder(binding, onContactClick)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContactDiffUtil : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return newItem == oldItem
        }

    }

    class ContactHolder(
        private val binding: ItemContactBinding,
        onContactClick: (Contact) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentContact: Contact? = null

        init {
            itemView.setOnClickListener { currentContact?.let(onContactClick) }
        }

        fun bind(contact: Contact) {
            with(binding) {
                currentContact = contact
                itemContactNameTextView.text = contact.name
                itemContactPhoneTextview.text = contact.phones.joinToString("\n")
            }
        }
    }
}