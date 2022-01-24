package com.example.wildlife.domain.useCases

import com.example.wildlife.data.repositories.AnimalRepository
import com.example.wildlife.domain.models.Animal
import kotlinx.coroutines.flow.Flow

class GetStoredAnimalsUseCase(private val repository: AnimalRepository) {
    fun execute(): Flow<List<Animal>> {
        return repository.storedAnimals
    }
}
