package ru.orlovegor.daoroom.database.contract.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.orlovegor.daoroom.database.contract.contract.RecordContract
import ru.orlovegor.daoroom.database.contract.models.data.Record

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(record: List<Record>)

    @Query("SELECT * FROM ${RecordContract.TABLE_NAME} WHERE ${RecordContract.Columns.PATIENT_ID} = :patientId")
    fun getPatientRecords(patientId: Long): Flow<List<Record>>

    @Query("Update ${RecordContract.TABLE_NAME} SET ${RecordContract.Columns.DIAGNOSIS} = :diagnosis WHERE ${RecordContract.Columns.ID} = :recordId")
    fun updateDiagnosisRecord(diagnosis: String, recordId: Long)

}