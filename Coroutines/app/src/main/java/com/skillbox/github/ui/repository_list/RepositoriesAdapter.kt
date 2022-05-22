package com.skillbox.github.ui.repository_list

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.data.RemoteRepository

class RepositoriesAdapter(
    private val onItemClick: (AvatarLink: String, name: String, owner: String) -> Unit
) : AsyncListDifferDelegationAdapter<RemoteRepository>(RepositoryDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(RepositoriesAdapterDelegate(onItemClick))
    }

    class RepositoryDiffUtilCallback : DiffUtil.ItemCallback<RemoteRepository>() {
        override fun areItemsTheSame(
            oldItem: RemoteRepository,
            newItem: RemoteRepository
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RemoteRepository,
            newItem: RemoteRepository
        ): Boolean {
            return oldItem == newItem
        }
    }
}