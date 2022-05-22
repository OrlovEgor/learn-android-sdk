package ru.orlovegor.daoroom.database.contract.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.orlovegor.daoroom.database.contract.contract.DoctorContract
import ru.orlovegor.daoroom.database.contract.contract.PatientContract
import ru.orlovegor.daoroom.database.contract.models.data.DoctorAndPatient
import ru.orlovegor.daoroom.database.contract.models.relationship.DoctorWithPatients
import ru.orlovegor.daoroom.database.contract.models.relationship.PatientWithDoctors


@Dao
interface DoctorAndPatientDao {
    @Transaction
    @Query("SELECT * FROM ${DoctorContract.TABLE_NAME} WHERE ${DoctorContract.Columns.ID} = :doctorId")
    fun getDoctorWithPatient(doctorId: Long): Flow<DoctorWithPatients>

    @Transaction
    @Query("SELECT * FROM ${PatientContract.TABLE_NAME} WHERE ${PatientContract.Columns.ID} = :patientId")
    fun getPatientWitchDoctor(patientId: Long): PatientWithDoctors

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDoctorWithPatient(doctorAndPatient: DoctorAndPatient)
}