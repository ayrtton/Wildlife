package com.example.wildlife.domain.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize @Entity(tableName = "animals_table")
data class Animal (
    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo(name = "specie")
    val specie: String,

    @Json(name = "scientific_name") @ColumnInfo(name = "scientific_name")
    val scientificName: String,

    @Json(name = "img_src") @ColumnInfo(name = "img_src")
    val imgSrc: String,

    @ColumnInfo(name = "group")
    val group: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @Json(name = "reference_link") @ColumnInfo(name = "reference_link")
    val referenceLink: String?,
) : Parcelable