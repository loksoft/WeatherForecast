package com.weather.weatherforecast


import android.content.Context
import androidx.room.Room
import com.weather.weatherforecast.data.WeatherApi
import com.weather.weatherforecast.data.WeatherDao
import com.weather.weatherforecast.data.WeatherDatabase
import com.weather.weatherforecast.data.WeatherRepositoryImpl
import com.weather.weatherforecast.domain.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.open-meteo.com/v1/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWeatherApi(
        retrofit: Retrofit
    ): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_db"
        ).build()

    @Provides
    fun provideWeatherDao(
        database: WeatherDatabase
    ): WeatherDao =
        database.weatherDao()

    @Provides
    @Singleton
    fun provideRepository(
        api: WeatherApi,
        dao: WeatherDao
    ): WeatherRepository =
        WeatherRepositoryImpl(api, dao)
}