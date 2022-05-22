package ru.orlovegor.daoroom.database.contract.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.orlovegor.daoroom.database.contract.contract.HospitalContract
import ru.orlovegor.daoroom.database.contract.models.data.Hospital

@Dao
interface HospitalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHospital(hospital: List<Hospital>)

    @Query(
        "SELECT ${HospitalContract.Columns.TITTLE} FROM  ${HospitalContract.TABLE_NAME}" +
                " WHERE ${HospitalContract.Columns.ID} = :hospitalId"
    )
    fun getHospitalNameById(hospitalId: Long): String

    @Query("SELECT * FROM ${HospitalContract.TABLE_NAME}")
    fun getHospitals(): List<Hospital>

    @Query(
        "SELECT ${HospitalContract.Columns.ID} FROM  ${HospitalContract.TABLE_NAME} WHERE ${HospitalContract.Columns.TITTLE} = :hospitalName"
    )
    fun getHospitalIdByName(hospitalName: String): Long
}