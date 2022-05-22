package ru.orlovegor.daoroom.database.contract.dao

import androidx.room.*
import ru.orlovegor.daoroom.database.contract.contract.CabinetContract
import ru.orlovegor.daoroom.database.contract.models.data.Cabinet
import ru.orlovegor.daoroom.database.contract.models.relationship.DoctorAndCabinet

@Dao
interface CabinetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCabinet(cabinet: List<Cabinet>)

    @Transaction
    @Query("SELECT * FROM ${CabinetContract.TABLE_NAME} WHERE ${CabinetContract.Columns.ID} = :cabinetId")
    fun getDoctorAndCabinet(cabinetId: Long): List<DoctorAndCabinet>

    @Query("SELECT ${CabinetContract.Columns.NUMBER} FROM ${CabinetContract.TABLE_NAME} WHERE ${CabinetContract.Columns.ID} = :cabinetId ")
    fun getCabinetNumberFromId(cabinetId: Long): Int

    @Query("SELECT * FROM ${CabinetContract.TABLE_NAME}")
    fun getCabinets(): List<Cabinet>

    @Query("SELECT ${CabinetContract.Columns.ID} FROM ${CabinetContract.TABLE_NAME} WHERE ${CabinetContract.Columns.NUMBER} = :cabinetNumber ")
    fun getCabinetIdByNumber(cabinetNumber: Int): Long
}