package ru.orlovegor.daoroom.database.contract.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.orlovegor.daoroom.database.contract.contract.PatientContract
import ru.orlovegor.daoroom.database.contract.contract.RecordContract

@Entity(tableName = RecordContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = [PatientContract.Columns.ID],
            childColumns = [RecordContract.Columns.PATIENT_ID]
        )
    ]
)
data class Record
    (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = RecordContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = RecordContract.Columns.PATIENT_ID)
    val patientId: Long,
    @ColumnInfo(name = RecordContract.Columns.DIAGNOSIS)
    val diagnosis: String,
    @ColumnInfo(name = RecordContract.Columns.THERAPY)
    val therapy: String
)
