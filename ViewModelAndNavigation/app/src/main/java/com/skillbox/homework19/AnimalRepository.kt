package com.skillbox.homework19

import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import com.skillbox.homework19.DataClasses.Animal
import kotlin.random.Random

class AnimalRepository {

    fun createNewAnimal(
        isWild: Boolean,
        name: String,
        age: Int,
        isAggressive: Boolean?
    ): Animal {
        return if (isWild) {
            Animal.Wild(
                name = name,
                age = age,
                photoLink = "https://cdn.pixabay.com/photo/2017/01/31/23/42/animal-2028258__340.png",
                aggression = when (isAggressive) {
                    true -> "yes"
                    false -> "no"
                    else -> ""
                },
                id = Random.nextLong()
            )
        } else {
            Animal.Pet(
                name = name,
                age = age,
                photoLink = "https://cdn.pixabay.com/photo/2016/07/14/16/39/dog-1517090__340.png",
                id = Random.nextLong()
            )
        }
    }

    fun deleteAnimal(animals: ArrayList<Animal>, position: Int): ArrayList<Animal> {
        return animals.filterIndexed { index, _ -> index != position } as ArrayList<Animal>
    }
}