package ru.orlovegor.daoroom.database.contract.dao

import androidx.room.*
import ru.orlovegor.daoroom.database.contract.contract.PatientContract
import ru.orlovegor.daoroom.database.contract.models.data.Patient

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPatient(patient: Patient): Long

    @Query("DELETE  FROM ${PatientContract.TABLE_NAME} WHERE ${PatientContract.Columns.ID} = :patientId")
    fun deletePatient(patientId: Long)
}