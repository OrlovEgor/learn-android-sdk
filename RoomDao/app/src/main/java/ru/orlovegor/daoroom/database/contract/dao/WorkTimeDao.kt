package ru.orlovegor.daoroom.database.contract.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.orlovegor.daoroom.database.contract.contract.WorkTimeContract
import ru.orlovegor.daoroom.database.contract.models.data.WorkTime

@Dao
interface WorkTimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkTime(workTime: List<WorkTime>)

    @Query("SELECT * FROM ${WorkTimeContract.TABLE_NAME}")
    fun getWorkTimes(): List<WorkTime>

    @Query(
        "SELECT * FROM ${WorkTimeContract.TABLE_NAME} WHERE ${WorkTimeContract.Columns.ID} = :workTimeId"
    )
    fun getWorkTimeById(workTimeId: Long): WorkTime

    @Query(
        "SELECT ${WorkTimeContract.Columns.ID} FROM ${WorkTimeContract.TABLE_NAME}" +
                " WHERE ${WorkTimeContract.Columns.TIME_START} = :timeStart" +
                " AND ${WorkTimeContract.Columns.TIME_END} = :timeEnd "
    )
    fun getWorkTimeIdByTime(timeStart: String, timeEnd: String): Long
}