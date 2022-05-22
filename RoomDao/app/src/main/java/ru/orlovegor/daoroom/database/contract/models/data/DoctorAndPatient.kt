package ru.orlovegor.daoroom.database.contract.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey.CASCADE
import ru.orlovegor.daoroom.database.contract.contract.DoctorAndPatientContract
import ru.orlovegor.daoroom.database.contract.contract.DoctorContract
import ru.orlovegor.daoroom.database.contract.contract.PatientContract

@Entity(
    primaryKeys = [DoctorContract.Columns.ID, PatientContract.Columns.ID],
    foreignKeys = [androidx.room.ForeignKey(
        entity = Doctor::class,
        parentColumns = [DoctorContract.Columns.ID],
        childColumns = [DoctorAndPatientContract.Columns.DOCTOR_ID],
        onDelete = CASCADE
    ),
        androidx.room.ForeignKey(
            entity = Patient::class,
            parentColumns = [PatientContract.Columns.ID],
            childColumns = [DoctorAndPatientContract.Columns.PATIENT_ID],
            onDelete = CASCADE
        )]
)
data class DoctorAndPatient
    (
    @ColumnInfo(name = PatientContract.Columns.ID)
    val patientId: Long,
    @ColumnInfo(name = DoctorContract.Columns.ID)
    val doctorId: Long
)




