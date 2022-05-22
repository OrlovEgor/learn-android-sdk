package ru.orlovegor.daoroom.database.contract.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.orlovegor.daoroom.database.contract.contract.WorkTimeContract

@Entity(tableName = WorkTimeContract.TABLE_NAME)
data class WorkTime
    (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WorkTimeContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = WorkTimeContract.Columns.TIME_START)
    val timeStart: String,
    @ColumnInfo(name = WorkTimeContract.Columns.TIME_END)
    val timeEnd: String

)
fun WorkTime.mapToTimeWork() = "${this.timeStart} - ${this.timeEnd}"

