package ru.orlovegor.daoroom.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovegor.daoroom.database.contract.models.data.Patient
import ru.orlovegor.daoroom.databinding.ItemPatientBinding
import ru.orlovegor.daoroom.ui.adapters.PatientAdapter.*

class PatientAdapter(
    private val onClickItem: (patientId: Long) -> Unit,
    private val onLongClick: (PatientId: Long) -> Unit
) :
    ListAdapter<Patient, PatientHolder>(PatientDiffUtils()) {

    class PatientDiffUtils : DiffUtil.ItemCallback<Patient>() {

        override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHolder {
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientHolder(binding, onClickItem, onLongClick)
    }

    override fun onBindViewHolder(holder: PatientHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PatientHolder(
        private val binding: ItemPatientBinding,
        onClickItem: (patientId: Long) -> Unit,
        onLongClick: (PatientId: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var patientId: Long? = null

        init {
            itemView.setOnClickListener {
                patientId?.let { it1 -> onClickItem(it1) }
            }
            itemView.setOnLongClickListener {
                patientId?.let { it1 -> onLongClick(it1) }
                true
            }
        }

        fun bind(patient: Patient) {
            patientId = patient.id
            with(binding) {
                patientId = patient.id
                patientFirstName.text = patient.firstName
                patientLastName.text = patient.lastName
            }
        }
    }
}