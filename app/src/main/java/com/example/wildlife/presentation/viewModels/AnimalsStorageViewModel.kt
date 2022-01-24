package com.example.wildlife.presentation.viewModels

import androidx.lifecycle.*
import com.example.wildlife.data.repositories.AnimalRepository
import com.example.wildlife.domain.models.Animal
import com.example.wildlife.domain.useCases.DeleteAnimalUseCase
import com.example.wildlife.domain.useCases.GetStoredAnimalsUseCase
import com.example.wildlife.domain.useCases.SaveAnimalUseCase
import kotlinx.coroutines.launch

enum class WildlifeDBStatus { LOADING, ERROR, DONE }

class AnimalsStorageViewModel(private val animalRepository: AnimalRepository) : ViewModel() {
    private val _status = MutableLiveData<WildlifeDBStatus>()
    val status: LiveData<WildlifeDBStatus>
        get() = _status

    lateinit var storedAnimals: LiveData<List<Animal>>

    init {
        getStoredAnimals()
    }

    private fun getStoredAnimals() {
        viewModelScope.launch {
            try {
                storedAnimals = GetStoredAnimalsUseCase(animalRepository).execute().asLiveData()
                _status.value = WildlifeDBStatus.DONE
            } catch (e: Exception) {
                _status.value = WildlifeDBStatus.ERROR
            }
        }
    }

    fun insert(animal: Animal) = viewModelScope.launch {
        SaveAnimalUseCase(animalRepository).execute(animal)
    }

    fun delete(animal: Animal) = viewModelScope.launch {
        DeleteAnimalUseCase(animalRepository).execute(animal)
    }
}

class AnimalsStorageViewModelFactory(private val repository: AnimalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimalsStorageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimalsStorageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}