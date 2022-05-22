package ru.orlovegor.daoroom.database.contract.contract

object PatientContract {

    const val TABLE_NAME = "patient"

    object Columns {
        const val ID = "patient_id"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
    }
}