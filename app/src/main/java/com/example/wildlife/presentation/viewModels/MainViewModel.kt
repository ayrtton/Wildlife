package com.example.wildlife.presentation.viewModels

import androidx.lifecycle.*
import com.example.wildlife.data.repositories.AnimalRepository
import com.example.wildlife.domain.models.Animal
import com.example.wildlife.data.web.WildlifeAPIFilter
import com.example.wildlife.domain.useCases.GetAnimalsUseCase
import com.example.wildlife.domain.useCases.GetStoredAnimalsUseCase
import com.example.wildlife.domain.useCases.SaveAnimalUseCase
import kotlinx.coroutines.launch

enum class WildlifeAPIStatus { LOADING, ERROR, DONE }

class MainViewModel(private val animalRepository: AnimalRepository) : ViewModel() {
    private val _status = MutableLiveData<WildlifeAPIStatus>()
    val status: LiveData<WildlifeAPIStatus>
        get() = _status

    private val _animals = MutableLiveData<List<Animal>>()
    val animals: LiveData<List<Animal>>
        get() = _animals

    init {
        getAnimals(WildlifeAPIFilter.SHOW_ALL)
    }

    private fun getAnimals(filter: WildlifeAPIFilter) {
        viewModelScope.launch {
            try {
                _animals.value = GetAnimalsUseCase(animalRepository).execute(filter.toString())
                _status.value = WildlifeAPIStatus.DONE
            } catch (e: Exception) {
                _status.value = WildlifeAPIStatus.ERROR
                _animals.value = ArrayList()
            }
        }
    }

    fun updateFilter(filter: WildlifeAPIFilter) {
        getAnimals(filter)
    }
}

class MainViewModelFactory(private val repository: AnimalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}