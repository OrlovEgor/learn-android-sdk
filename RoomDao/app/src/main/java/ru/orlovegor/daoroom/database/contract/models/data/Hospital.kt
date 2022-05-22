package ru.orlovegor.daoroom.database.contract.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.orlovegor.daoroom.database.contract.contract.HospitalContract

@Entity(tableName = HospitalContract.TABLE_NAME)
data class Hospital
    (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = HospitalContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = HospitalContract.Columns.TITTLE)
    val tittle: String,
    @ColumnInfo(name = HospitalContract.Columns.ADDRESS)
    val address: String
)
