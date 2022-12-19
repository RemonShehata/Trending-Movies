package com.example.trendingmovies.core.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trendingmovies.core.source.local.models.ConfigurationEntity

@Dao
interface ConfigurationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfiguration(configuration: ConfigurationEntity): Long

    @Query("SELECT * FROM ConfigurationEntity")
    suspend fun getConfigurationsSync(): ConfigurationEntity?
}