package com.example.wildlife.domain.useCases

import com.example.wildlife.data.repositories.AnimalRepository
import com.example.wildlife.domain.models.Animal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveAnimalUseCase(private val repository: AnimalRepository)  {
    suspend fun execute(param: Animal) {
        repository.insert(param)
    }
}