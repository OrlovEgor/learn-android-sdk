package ru.orlovegor.daoroom.database.contract.models.data

import androidx.room.TypeConverter

class DoctorSpecializationsConverter {
    @TypeConverter
    fun convertSpecializationToString(specializations: DoctorSpecializations) = specializations.name

    @TypeConverter
    fun convertStringToSpecialization(specializationString: String) =
        DoctorSpecializations.valueOf(specializationString)
}