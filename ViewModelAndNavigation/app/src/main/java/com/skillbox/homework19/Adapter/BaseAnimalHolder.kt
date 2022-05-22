package com.skillbox.homework19.Adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.skillbox.homework19.R

abstract class BaseAnimalHolder(
    view: ConstraintLayout,
    onLongItemClick: (position: Int) -> Unit,
    onItemClick: (id: Long, name: String) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
    private val ageTextView = view.findViewById<TextView>(R.id.ageTextView)
    private val photoImageView = view.findViewById<ImageView>(R.id.avatarImageView)

    private var currentId: Long? = null
    private var currentName: String? = null

    init {
        view.setOnClickListener {
            currentName?.let { it1 -> currentId?.let { it2 -> onItemClick(it2, it1) } }
        }

        view.setOnLongClickListener {
            onLongItemClick(adapterPosition)
            true
        }
    }

    protected fun bindMainInfo(
        id: Long,
        name: String,
        age: Int,
        photoLink: String
    ) {
        currentName = name
        currentId = id
        nameTextView.text = name
        ageTextView.text =
            itemView.context.resources.getString(R.string.text_age) + age.toString()

        Glide.with(itemView)
            .load(photoLink)
            .error(R.drawable.ic_error)
            .placeholder(R.drawable.ic_portrait)
            .into(photoImageView)
    }
}
