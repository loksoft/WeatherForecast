package com.weather.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/** Room database abstract class **/
@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}