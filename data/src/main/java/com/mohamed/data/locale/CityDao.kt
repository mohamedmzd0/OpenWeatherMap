package com.mohamed.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohamed.data.models.CityData

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityData>)

    @Query("SELECT * FROM cities")
    suspend fun getAllCities(): List<CityData>?

    @Query("SELECT * FROM cities WHERE id = :cityId")
    suspend fun getCityById(cityId: Int): CityData?
}
