package ru.orlovegor.daoroom.database.contract.contract

object DoctorContract {

    const val TABLE_NAME = "doctor"

    object Columns {
        const val ID = "doctor_id"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val HOSPITAL_ID = "hospital_id"
        const val WORK_TIME_ID = "work_time_id"
        const val SPECIALIZATION = "specialization"
        const val CABINET_ID = "cabinet_id"
    }
}