package ru.orlovegor.daoroom.database.contract.models.relationship

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.orlovegor.daoroom.database.contract.contract.DoctorContract
import ru.orlovegor.daoroom.database.contract.contract.PatientContract
import ru.orlovegor.daoroom.database.contract.models.data.Doctor
import ru.orlovegor.daoroom.database.contract.models.data.Patient
import ru.orlovegor.daoroom.database.contract.models.data.DoctorAndPatient


data class PatientWithDoctors(
    @Embedded
    val patient: Patient,
    @Relation(
        parentColumn = PatientContract.Columns.ID,
        entityColumn = DoctorContract.Columns.ID,
        associateBy = Junction(DoctorAndPatient::class)
    )
    val doctors: List<Doctor>
)


