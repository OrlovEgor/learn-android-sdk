package com.skillbox.github.ui.repository_list


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.github.R
import com.skillbox.github.data.RemoteRepository
import com.skillbox.github.databinding.ItemRepositoryBinding

class RepositoriesAdapterDelegate(
    private val onItemClick: (avatarLink: String, name: String, owner: String) -> Unit
) : AbsListItemAdapterDelegate<RemoteRepository, RemoteRepository, RepositoriesAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: RemoteRepository,
        items: MutableList<RemoteRepository>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(
            onItemClick,
            binding
        )
    }

    override fun onBindViewHolder(
        item: RemoteRepository,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        onItemClick: (avatarLink: String, name: String, owner: String) -> Unit,
        private val binding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentLink: String? = null
        private var currentName: String? = null
        private var currentOwner: String? = null

        init {
            itemView.setOnClickListener {
                currentLink?.let { it1 ->
                    currentName?.let { it2 ->
                        currentOwner?.let { it3 ->
                            onItemClick(
                                it1,
                                it2,
                                it3
                            )
                        }
                    }
                }
            }
        }

        fun bind(item: RemoteRepository) {
            currentLink = item.userData.avatarLink
            currentName = item.name
            currentOwner = item.userData.ownerName
            with(binding) {
                repositoryNameTextView.text = item.name
                Glide.with(itemView)
                    .load(item.userData.avatarLink)
                    .error(R.drawable.error_24)
                    .into(repositoryImageView)
            }
        }
    }
}
