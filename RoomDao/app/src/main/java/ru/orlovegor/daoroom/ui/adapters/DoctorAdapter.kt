package ru.orlovegor.daoroom.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovegor.daoroom.databinding.ItemDoctorBinding
import ru.orlovegor.daoroom.ui.models.DoctorRW

class DoctorAdapter(
    private val onItemClick: (doctorId: Long) -> Unit,
    private val onLongClick: (doctorId: Long) -> Unit
) :
    ListAdapter<DoctorRW, DoctorAdapter.Holder>(DoctorDiffUtils()) {

    class DoctorDiffUtils : DiffUtil.ItemCallback<DoctorRW>() {

        override fun areItemsTheSame(oldItem: DoctorRW, newItem: DoctorRW): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DoctorRW, newItem: DoctorRW): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemDoctorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding, onItemClick, onLongClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(
        private val binding: ItemDoctorBinding,
        onItemClick: (doctorId: Long) -> Unit,
        onLongClick: (doctorId: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var doctorId: Long? = null

        init {
            itemView.setOnClickListener {
                doctorId?.let(onItemClick)
            }
            itemView.setOnLongClickListener {
                doctorId?.let(onLongClick)
                true
            }
        }

        fun bind(doctor: DoctorRW) {
            doctorId = doctor.id
            with(binding) {
                doctorFirstNameText.text = doctor.firstName
                doctorLastNameText.text = doctor.lastName
                doctorHospitalText.text = doctor.hospitalName
                doctorCabinetText.text = doctor.cabinetNumber.toString()
                doctorSpecializationText.text = doctor.specialization
                doctorOpeningHoursText.text = doctor.workTime
            }
        }
    }
}

