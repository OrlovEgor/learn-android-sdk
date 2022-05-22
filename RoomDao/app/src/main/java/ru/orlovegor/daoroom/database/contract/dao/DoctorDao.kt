package ru.orlovegor.daoroom.database.contract.dao

import androidx.room.*
import ru.orlovegor.daoroom.database.contract.contract.DoctorContract
import ru.orlovegor.daoroom.database.contract.models.data.Doctor


@Dao
interface DoctorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDoctor(doctor: List<Doctor>)

    @Query("SELECT * FROM ${DoctorContract.TABLE_NAME}")
    fun getDoctors(): List<Doctor>

    @Query("DELETE  FROM ${DoctorContract.TABLE_NAME} WHERE ${DoctorContract.Columns.ID} = :doctorId")
    fun deleteDoctor(doctorId: Long)

}