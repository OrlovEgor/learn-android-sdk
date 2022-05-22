package com.skillbox.homework19

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.homework19.DataClasses.Animal
import com.skillbox.homework19.Utils.SingleLiveEvent

class AnimalViewModel : ViewModel() {

    private val mutableAnimalLiveData = MutableLiveData<ArrayList<Animal>>(arrayListOf())

    private val repository = AnimalRepository()
    val animalLiveData: LiveData<ArrayList<Animal>>
        get() = mutableAnimalLiveData

    private val showToastLiveData = SingleLiveEvent<Unit>()
    val showToast: LiveData<Unit>
        get() = showToastLiveData

    fun addAnimal(
        isWild: Boolean,
        name: String,
        age: Int,
        isAggressive: Boolean?
    ) {
        val newAnimal = repository.createNewAnimal(
            isWild = isWild,
            name = name,
            age = age,
            isAggressive = isAggressive
        )
        val updatedList = arrayListOf(newAnimal) + mutableAnimalLiveData.value.orEmpty()
        mutableAnimalLiveData.postValue(updatedList as ArrayList<Animal>)
    }

    fun deleteAnimal(position: Int) {
        mutableAnimalLiveData.postValue(
            repository.deleteAnimal(
                mutableAnimalLiveData.value.orEmpty() as ArrayList<Animal>, position
            )
        )
        showToastLiveData.postValue(Unit)
    }
}
