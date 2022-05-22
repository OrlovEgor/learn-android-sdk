package ru.orlovegor.daoroom.database.contract.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.orlovegor.daoroom.database.contract.contract.PatientContract

@Entity(tableName = PatientContract.TABLE_NAME)
data class Patient
    (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PatientContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = PatientContract.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = PatientContract.Columns.LAST_NAME)
    val lastName: String
)
