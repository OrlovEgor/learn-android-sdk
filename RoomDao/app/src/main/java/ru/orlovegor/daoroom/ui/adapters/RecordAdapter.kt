package ru.orlovegor.daoroom.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovegor.daoroom.database.contract.models.data.Record

import ru.orlovegor.daoroom.databinding.ItemRecordBinding

class RecordAdapter(
    private val onClickItem: (patientId: Long) -> Unit,
    private val onLongClick: (PatientId: Long) -> Unit
) :
    ListAdapter<Record, RecordAdapter.RecordsHolder>(RecordsDiffUtils()) {

    class RecordsDiffUtils : DiffUtil.ItemCallback<Record>() {

        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsHolder {
        val binding = ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordsHolder(binding, onClickItem, onLongClick)
    }

    override fun onBindViewHolder(holder: RecordsHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RecordsHolder(
        private val binding: ItemRecordBinding,
        onClickItem: (patientId: Long) -> Unit,
        onLongClick: (PatientId: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var recordId: Long? = null

        init {
            itemView.setOnClickListener {
                recordId?.let { it1 -> onClickItem(it1) }
            }
            itemView.setOnLongClickListener {
                recordId?.let { it1 -> onLongClick(it1) }
                true
            }
        }

        fun bind(record: Record) {
            recordId = record.id
            with(binding) {
                recordText.text = record.diagnosis
                recordTherapyText.text = record.therapy

            }
        }
    }
}