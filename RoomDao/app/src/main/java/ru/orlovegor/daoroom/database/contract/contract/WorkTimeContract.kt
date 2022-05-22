package ru.orlovegor.daoroom.database.contract.contract

object WorkTimeContract {

    const val TABLE_NAME = "work_time"

    object Columns{
        const val ID = "work_time_id"
        const val TIME_START = "time_start"
        const val TIME_END = "time_end"
    }
}