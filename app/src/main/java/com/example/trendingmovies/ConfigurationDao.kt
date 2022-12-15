package com.example.trendingmovies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trendingmovies.database.ConfigurationEntity

@Dao
interface ConfigurationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfiguration(configuration: ConfigurationEntity): Long

    @Query("SELECT * FROM ConfigurationEntity")
    suspend fun getConfigurationsSync(): ConfigurationEntity?
}