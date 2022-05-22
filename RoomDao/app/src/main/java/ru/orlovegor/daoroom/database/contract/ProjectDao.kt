package ru.orlovegor.daoroom.database.contract

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.orlovegor.daoroom.database.contract.ProjectDao.Companion.DB_VERSION
import ru.orlovegor.daoroom.database.contract.dao.*
import ru.orlovegor.daoroom.database.contract.models.data.*

@Database(
    entities = [
        Cabinet::class,
        Doctor::class,
        Hospital::class,
        Patient::class,
        Record::class,
        WorkTime::class,
        DoctorAndPatient::class
    ],
    version = DB_VERSION
)
abstract class ProjectDao : RoomDatabase() {

    abstract fun cabinetDao(): CabinetDao
    abstract fun doctorDao(): DoctorDao
    abstract fun hospitalDao(): HospitalDao
    abstract fun patientDao(): PatientDao
    abstract fun recordDao(): RecordDao
    abstract fun workTimeDao(): WorkTimeDao
    abstract fun doctorAndPatientDao(): DoctorAndPatientDao

    companion object {
        const val DB_VERSION = 2
        const val DB_NAME = "Project_Database"
    }
}