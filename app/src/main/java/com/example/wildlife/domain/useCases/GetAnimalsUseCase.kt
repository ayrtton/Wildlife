package com.example.wildlife.domain.useCases

import com.example.wildlife.data.repositories.AnimalRepository
import com.example.wildlife.data.web.WildlifeAPIFilter
import com.example.wildlife.domain.models.Animal

class GetAnimalsUseCase(private val repository: AnimalRepository) {
    suspend fun execute(param: String): List<Animal> {
        val group = WildlifeAPIFilter.values().find { it.name == param }
        return repository.getAnimalsFromAPI(group!!)
    }
}