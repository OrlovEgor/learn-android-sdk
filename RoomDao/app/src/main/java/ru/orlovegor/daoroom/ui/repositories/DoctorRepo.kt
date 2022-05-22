package ru.orlovegor.daoroom.ui.repositories

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.orlovegor.daoroom.database.contract.Database
import ru.orlovegor.daoroom.database.contract.models.data.*
import ru.orlovegor.daoroom.ui.models.DoctorRW

class DoctorRepo(context: Context) {

    private val sharedPrefs by lazy {
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    private val doctorDao = Database.instance.doctorDao()
    private val hospitalDao = Database.instance.hospitalDao()
    private val workTimeDao = Database.instance.workTimeDao()
    private val cabinetDao = Database.instance.cabinetDao()

    suspend fun deleteDoctor(doctorId: Long) {
        withContext(Dispatchers.IO) {
            doctorDao.deleteDoctor(doctorId)
        }
    }

    suspend fun getDoctorsLocal() =
        withContext(Dispatchers.IO) {
            val doctors = doctorDao.getDoctors()
            val listDoctorRw = arrayListOf<DoctorRW>()
            doctors.forEach { doctor ->
                listDoctorRw.add(
                    DoctorRW(
                        id = doctor.id,
                        firstName = doctor.firstName,
                        lastName = doctor.lastName,
                        hospitalName = Database.instance.hospitalDao()
                            .getHospitalNameById(doctor.hospitalId),
                        cabinetNumber = Database.instance.cabinetDao()
                            .getCabinetNumberFromId(doctor.cabinetId),
                        specialization = doctor.specialization.name,
                        workTime = workTimeDao.getWorkTimeById(doctor.workTimeId).mapToTimeWork()
                    )
                )
            }
         listDoctorRw
        }

    suspend fun insertDoctorLocal(
        doctorRW: ArrayList<DoctorRW>
    ) {
        withContext(Dispatchers.IO) {
            val doctor = doctorRW.first()
            val newDoctor = Doctor(
                id = 0,
                firstName = doctor.firstName,
                lastName = doctor.lastName,
                hospitalId = hospitalDao.getHospitalIdByName(doctor.hospitalName),
                workTimeId = workTimeDao.getWorkTimeIdByTime(
                    doctor.workTime.take(SYMBOL_COUNT),
                    doctor.workTime.takeLast(SYMBOL_COUNT)
                ),
                specialization = DoctorSpecializations.valueOf(doctor.specialization),
                cabinetId = cabinetDao.getCabinetIdByNumber(doctor.cabinetNumber)
            )
            doctorDao.insertDoctor(listOf(newDoctor))
        }
    }

    suspend fun getSpecializationName() = withContext(Dispatchers.IO) {
        DoctorSpecializations.values().map { it.name }
    }

    suspend fun getCabinetsNumber() = withContext(Dispatchers.IO) {
        Database.instance.cabinetDao().getCabinets().map { it.number.toString() }
    }

    suspend fun getHospitalName() = withContext(Dispatchers.IO) {
        Database.instance.hospitalDao().getHospitals().map { it.tittle }
    }

    suspend fun getWorkTimes() = withContext(Dispatchers.IO) {
        Database.instance.workTimeDao().getWorkTimes().map { "${it.timeStart} - ${it.timeEnd}" }
    }

    suspend fun addFlagToFirsLaunch() {
        withContext(Dispatchers.IO) {
            val key = FIRST_LAUNCH_KEY
            val value = FIRST_LAUNCH_VALUE
            sharedPrefs.edit()
                .putString(key, value)
                .commit()
        }
    }

    suspend fun isFirstLaunch(): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext (sharedPrefs.contains(FIRST_LAUNCH_KEY))
        }
    }

    suspend fun addStandardValues() {
        withContext(Dispatchers.IO) {
            Database.instance.hospitalDao().insertHospital(
                listOf(
                    Hospital(0, "Saint_Victoria", "Carolina,Grow street 54/1"),
                    Hospital(0, "Mother heart", "Virginia, Hoop street, 44/7")
                )
            )
            Database.instance.cabinetDao().insertCabinet(
                listOf(
                    Cabinet(0, 12),
                    Cabinet(0, 31)
                )
            )
            Database.instance.workTimeDao().insertWorkTime(
                listOf(
                    WorkTime(0, "10.00AM", "11.00PM"),
                    WorkTime(0, "13.00PM", "14.30PM")
                )
            )
        }
    }

    companion object {
        private const val SHARED_PREFS_NAME = "File_shared_prefs"
        private const val FIRST_LAUNCH_KEY = "Is_first_launch"
        private const val FIRST_LAUNCH_VALUE = "true"
        private const val SYMBOL_COUNT = 7
    }
}