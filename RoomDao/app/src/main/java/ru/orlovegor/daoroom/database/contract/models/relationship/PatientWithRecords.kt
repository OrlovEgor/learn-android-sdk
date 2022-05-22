package ru.orlovegor.daoroom.database.contract.models.relationship

import androidx.room.Embedded
import androidx.room.Relation
import ru.orlovegor.daoroom.database.contract.contract.PatientContract
import ru.orlovegor.daoroom.database.contract.contract.RecordContract
import ru.orlovegor.daoroom.database.contract.models.data.Patient
import ru.orlovegor.daoroom.database.contract.models.data.Record

data class PatientWithRecords(
    @Embedded
    val patient: Patient,
    @Relation(
        parentColumn = PatientContract.Columns.ID,
        entityColumn = RecordContract.Columns.PATIENT_ID
    )
    val records: List<Record>
)
