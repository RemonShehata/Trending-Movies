package com.example.trendingmovies.core.source.repos

import com.example.trendingmovies.core.source.local.ConfigurationDao
import com.example.trendingmovies.core.source.local.models.ConfigurationEntity
import com.example.trendingmovies.core.source.remote.MoviesApi
import com.example.trendingmovies.utils.toConfigurationEntity
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val configurationDao: ConfigurationDao
) : ConfigurationRepo {

    override suspend fun getConfiguration(): ConfigurationEntity {
        val configurationEntity = configurationDao.getConfigurationsSync()
        if (configurationEntity != null) {
            return configurationEntity
        } else {
            moviesApi.getConfiguration().also { configurationResponse ->
                val configurationEntity = configurationResponse.toConfigurationEntity()
                configurationDao.insertConfiguration(configurationEntity)
                return configurationEntity
            }
        }
    }
}