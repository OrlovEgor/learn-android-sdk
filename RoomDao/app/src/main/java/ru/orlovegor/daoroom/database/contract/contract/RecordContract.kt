package ru.orlovegor.daoroom.database.contract.contract

object RecordContract {

    const val TABLE_NAME = "record"

    object Columns {
        const val ID = "record_id"
        const val PATIENT_ID = "patient_id"
        const val DIAGNOSIS = "diagnosis"
        const val THERAPY = "therapy"
    }
}