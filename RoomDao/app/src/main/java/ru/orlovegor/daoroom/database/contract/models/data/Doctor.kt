package ru.orlovegor.daoroom.database.contract.models.data

import androidx.room.*
import ru.orlovegor.daoroom.database.contract.contract.CabinetContract
import ru.orlovegor.daoroom.database.contract.contract.DoctorContract
import ru.orlovegor.daoroom.database.contract.contract.HospitalContract
import ru.orlovegor.daoroom.database.contract.contract.WorkTimeContract

@Entity(
    tableName = DoctorContract.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = WorkTime::class,
        parentColumns = [WorkTimeContract.Columns.ID],
        childColumns = [DoctorContract.Columns.WORK_TIME_ID]
    ),
        ForeignKey(
            entity = Hospital::class,
            parentColumns = [HospitalContract.Columns.ID],
            childColumns = [DoctorContract.Columns.HOSPITAL_ID]
        )]
    //indices = [Index(value = [DoctorContract.Columns.CABINET_ID], unique = true)]
)
@TypeConverters(DoctorSpecializationsConverter::class)
data class Doctor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DoctorContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = DoctorContract.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = DoctorContract.Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = DoctorContract.Columns.HOSPITAL_ID)
    val hospitalId: Long,
    @ColumnInfo(name = DoctorContract.Columns.WORK_TIME_ID)
    val workTimeId: Long,
    @ColumnInfo(name = DoctorContract.Columns.SPECIALIZATION)
    val specialization: DoctorSpecializations,
    @ColumnInfo(name = DoctorContract.Columns.CABINET_ID)
    val cabinetId: Long
)
