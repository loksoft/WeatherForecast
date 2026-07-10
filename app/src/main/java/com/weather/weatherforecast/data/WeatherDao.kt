package com.weather.weatherforecast.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/** Room database DAO **/
@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE city = :city")
    fun getWeather(city: String): Flow<WeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("DELETE FROM weather WHERE city = :city")
    suspend fun deleteWeather(city: String)
}