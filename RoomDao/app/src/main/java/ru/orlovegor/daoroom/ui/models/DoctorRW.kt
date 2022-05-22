package ru.orlovegor.daoroom.ui.models

data class DoctorRW(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val hospitalName: String,
    val workTime: String,
    val specialization: String,
    val cabinetNumber: Int
)
