package com.example.trendingmovies.core.source.repos

import com.example.trendingmovies.core.source.local.models.ConfigurationEntity

interface ConfigurationRepo {
    suspend fun getConfiguration(): ConfigurationEntity?
}