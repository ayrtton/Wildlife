package com.example.wildlife.data.repositories

import androidx.annotation.WorkerThread
import com.example.wildlife.data.database.AnimalDAO
import com.example.wildlife.domain.models.Animal
import com.example.wildlife.data.web.WildlifeAPI
import com.example.wildlife.data.web.WildlifeAPIFilter

class AnimalRepository(private val animalDAO: AnimalDAO) {
    suspend fun getAnimalsFromAPI(filter: WildlifeAPIFilter): List<Animal> {
        return WildlifeAPI.retrofitService.getAnimals(filter.group)
    }

    val storedAnimals = animalDAO.getAllAnimals()

    @WorkerThread
    suspend fun insert(animal: Animal): Long {
        return animalDAO.insertAnimal(animal)
    }

    @WorkerThread
    suspend fun delete(animal: Animal): Int {
        return animalDAO.deleteAnimal(animal)
    }
}

