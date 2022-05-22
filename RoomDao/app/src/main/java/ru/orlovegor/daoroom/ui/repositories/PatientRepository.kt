package ru.orlovegor.daoroom.ui.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.orlovegor.daoroom.database.contract.Database
import ru.orlovegor.daoroom.database.contract.models.data.DoctorAndPatient
import ru.orlovegor.daoroom.database.contract.models.data.Patient

class PatientRepository {

    private val patientDao = Database.instance.patientDao()
    private val doctorAndPatientDao = Database.instance.doctorAndPatientDao()

    fun getPatientsByDoctorLocal(doctorId: Long) =
        doctorAndPatientDao.getDoctorWithPatient(doctorId)

    suspend fun getDoctorsByPatientLocal(patientId: Long) = withContext(Dispatchers.IO) {
        doctorAndPatientDao.getPatientWitchDoctor(patientId).doctors.joinToString("") {
            "${it.firstName} ${it.lastName} ${it.specialization.name}"
        }
    }

    suspend fun deletePatient(patientId: Long) =
        withContext(Dispatchers.IO) { patientDao.deletePatient(patientId) }

    suspend fun insertDoctorAndPatientRelationship(
        doctorId: Long,
        firstName: String,
        lastname: String
    ) {
        withContext(Dispatchers.IO) {
            doctorAndPatientDao.insertDoctorWithPatient(
                DoctorAndPatient(
                    addPatient(firstName, lastname),
                    doctorId
                )
            )
        }
    }

    private fun addPatient(firstName: String, lastname: String) =
        patientDao.insertPatient(
            Patient(
                id = 0,
                firstName = firstName,
                lastName = lastname
            )
        )
}


