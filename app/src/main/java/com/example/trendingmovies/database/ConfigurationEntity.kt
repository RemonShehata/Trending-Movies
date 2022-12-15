package com.example.trendingmovies.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConfigurationEntity(
    // static id because we always want to update the values here
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: Int = 1,
    @ColumnInfo(name = "base_url") val baseUrl: String,
    @ColumnInfo(name = "secure_base_url") val secureBaseUrl: String,
    @ColumnInfo(name = "backdrop_sizes") val backdropSizes: List<String>,
    @ColumnInfo(name = "logo_sizes") val logoSizes: List<String>,
    @ColumnInfo(name = "poster_sizes") val posterSizes: List<String>,
    @ColumnInfo(name = "profile_sizes") val profileSizes: List<String>,
    @ColumnInfo(name = "still_sizes") val stillSizes: List<String>,
    @ColumnInfo(name = "change_keys") val changeKeys: List<String>,
)