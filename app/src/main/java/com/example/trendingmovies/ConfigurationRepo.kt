package com.example.trendingmovies

import com.example.trendingmovies.database.ConfigurationEntity

interface ConfigurationRepo {
    suspend fun getConfiguration(): ConfigurationEntity
}