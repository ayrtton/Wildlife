package com.example.wildlife.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wildlife.domain.models.Animal

@Database(entities = [Animal::class], version = 2, exportSchema = false)
abstract class AnimalDatabase: RoomDatabase() {
    abstract fun animalDAO(): AnimalDAO

    companion object {
        @Volatile
        private var INSTANCE: AnimalDatabase? = null

        fun getDatabase(context: Context): AnimalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AnimalDatabase::class.java, "animal_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}