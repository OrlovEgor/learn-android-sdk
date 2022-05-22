package ru.orlovegor.daoroom.ui.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.orlovegor.daoroom.database.contract.Database
import ru.orlovegor.daoroom.database.contract.models.data.Record

class RecordsRepository {

    private val recordsDao = Database.instance.recordDao()

    fun getRecordsByPatient(patientId: Long) = recordsDao.getPatientRecords(patientId)

    suspend fun insertRecord(patientId: Long, diagnosis: String,therapy: String) {
        withContext(Dispatchers.IO) {
            recordsDao.insertRecord(listOf(Record(0, patientId, diagnosis,therapy)))
        }
    }

    suspend fun updateDiagnosis(diagnosis: String, recordId: Long) {
        withContext(Dispatchers.IO) {
            recordsDao.updateDiagnosisRecord(diagnosis, recordId)
        }
    }
}