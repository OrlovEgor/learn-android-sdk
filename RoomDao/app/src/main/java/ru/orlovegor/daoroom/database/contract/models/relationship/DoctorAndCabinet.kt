package ru.orlovegor.daoroom.database.contract.models.relationship

import androidx.room.Embedded
import androidx.room.Relation
import ru.orlovegor.daoroom.database.contract.contract.CabinetContract
import ru.orlovegor.daoroom.database.contract.contract.DoctorContract
import ru.orlovegor.daoroom.database.contract.models.data.Cabinet
import ru.orlovegor.daoroom.database.contract.models.data.Doctor

data class DoctorAndCabinet(
    @Embedded val cabinet: Cabinet,
    @Relation(
        parentColumn = CabinetContract.Columns.ID,
        entityColumn = DoctorContract.Columns.CABINET_ID
    )
    val doctor: Doctor
)
