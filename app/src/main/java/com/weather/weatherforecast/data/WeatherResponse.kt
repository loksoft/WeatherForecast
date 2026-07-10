import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @SerializedName("current")
    val current: CurrentDto,

    @SerializedName("hourly")
    val hourly: HourlyDto,

    @SerializedName("daily")
    val daily: DailyDto
)

data class CurrentDto(

    @SerializedName("temperature_2m")
    val temperature: Double,

    @SerializedName("weather_code")
    val weatherCode: Int
)

data class HourlyDto(

    @SerializedName("time")
    val time: List<String>,

    @SerializedName("temperature_2m")
    val temperature: List<Double>
)

data class DailyDto(

    @SerializedName("time")
    val time: List<String>,

    @SerializedName("weather_code")
    val weatherCode: List<Int>,

    @SerializedName("temperature_2m_max")
    val maxTemperature: List<Double>,

    @SerializedName("temperature_2m_min")
    val minTemperature: List<Double>
)