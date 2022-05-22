package ru.orlovegor.daoroom.database.contract.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.orlovegor.daoroom.database.contract.contract.CabinetContract

@Entity(
    tableName = CabinetContract.TABLE_NAME
)
data class Cabinet
    (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CabinetContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = CabinetContract.Columns.NUMBER)
    val number: Int
)
