package com.mohamed.data.di

import com.mohamed.data.repository.GetCityRepositoryImpl
import com.mohamed.data.repository.GetWeatherRepositoryImpl
import com.mohamed.domain.repository.GetCityRepository
import com.mohamed.domain.repository.GetWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {

    @Binds
    fun bindGetWeatherRepository(getWeatherRepository: GetWeatherRepositoryImpl): GetWeatherRepository

    @Binds
    fun bindCitiesRepository(getWeatherRepository: GetCityRepositoryImpl): GetCityRepository

}
