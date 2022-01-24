package com.example.wildlife.data.web

import com.example.wildlife.domain.models.Animal
import retrofit2.http.GET
import retrofit2.http.Query

interface WildlifeAPIService {
    @GET("species.php")
    suspend fun getAnimals(@Query("group") type: String): List<Animal>
}