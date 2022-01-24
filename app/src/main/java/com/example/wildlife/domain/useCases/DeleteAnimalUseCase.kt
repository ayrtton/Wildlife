package com.example.wildlife.domain.useCases

import com.example.wildlife.data.repositories.AnimalRepository
import com.example.wildlife.domain.models.Animal

class DeleteAnimalUseCase(private val repository: AnimalRepository) {
    suspend fun execute(param: Animal) {
        repository.delete(param)
    }
}