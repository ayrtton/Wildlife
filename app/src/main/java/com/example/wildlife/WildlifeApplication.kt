package com.example.wildlife

import android.app.Application
import com.example.wildlife.data.database.AnimalDatabase
import com.example.wildlife.data.repositories.AnimalRepository

class WildlifeApplication: Application() {
    val database by lazy { AnimalDatabase.getDatabase(this) }
    val repository by lazy { AnimalRepository(database.animalDAO()) }
}