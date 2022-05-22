package ru.orlovegor.videostorage.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.orlovegor.videostorage.data.Video
import ru.orlovegor.videostorage.databinding.ItemVideoBinding

class VideoAdapter(
    private val onClick: (idVideo: Long) -> Unit
) : ListAdapter<Video, VideoAdapter.Holder>(VideoDiffUtils()) {
    class VideoDiffUtils : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemVideoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding, onClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(
        private val binding: ItemVideoBinding,
        onLongClick: (idVideo: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var idVideo: Long? = null

        init {
            itemView.setOnClickListener {
                idVideo?.let { onLongClick(it) }
            }
        }

        fun bind(video: Video) {
            with(binding) {
                idVideo = video.id
                itemVideoName.text = video.name
                itemVideoSize.text = video.size.toString()
                Glide.with(itemView)
                    .load(video.uri)
                    .into(itemVideoPreview)
            }
        }
    }
}