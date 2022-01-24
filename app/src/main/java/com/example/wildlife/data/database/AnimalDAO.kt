package com.example.wildlife.data.database

import androidx.room.*
import com.example.wildlife.domain.models.Animal
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDAO {
    @Insert
    suspend fun insertAnimal(animal: Animal): Long

    @Delete
    suspend fun deleteAnimal(animal: Animal): Int

    @Query("DELETE FROM animals_table")
    suspend fun deleteAllAnimals(): Int

    @Query("SELECT * FROM animals_table ORDER BY specie ASC")
    fun getAllAnimals(): Flow<List<Animal>>
}