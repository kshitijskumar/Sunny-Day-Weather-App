package com.example.sunnyday.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import java.lang.Exception

data class RequiredData(
    var status: String,
    var weatherType: String,
    var temp: String,
    var feelsLike: String,
    var minTemp: String,
    var maxTemp: String,
    var pressure: String,
    var humidity: String,
    var visibility: String,
    var windSpeed: String,
)

class MainRepository {

    private val weatherService by lazy { WeatherService.create() }

    suspend fun callApi(cityName: String="New Delhi"): RequiredData{
        val responseData= RequiredData(
            "none",
            "none",
            "none",
            "none",
            "none",
            "none",
            "none",
            "none",
            "none",
            "none",
        )
        Log.d("MainRepo", "Inside ${Thread.currentThread().name}")
        return  try{
            withContext(IO) {
                Log.d("MainRepo", "Inside wihtContext block and ${Thread.currentThread().name}")

                val response = weatherService.getWeather(cityName).awaitResponse()
                if (response.isSuccessful) {
                    Log.d("Repo", response.body()!!.name)

                    responseData.status = "Success"
                    responseData.weatherType = response.body()!!.weather[0].main
                    responseData.temp = response.body()!!.main.temp.toString() + "°C"
                    responseData.feelsLike = response.body()!!.main.feelsLike.toString() + "°C"
                    responseData.minTemp = response.body()!!.main.tempMin.toString() + "°C"
                    responseData.maxTemp = response.body()!!.main.tempMax.toString() + "°C"
                    responseData.pressure = response.body()!!.main.pressure.toString() + " hPa"
                    responseData.humidity = response.body()!!.main.humidity.toString() + " %"
                    responseData.visibility = response.body()!!.visibility.toString()
                    responseData.windSpeed = response.body()!!.wind.speed.toString() + " m/s"
                }

                responseData
            }
            }catch (e: Exception){
            e.printStackTrace()

            responseData
        }

        //Log.d("MainRepo","In thread ${Thread.currentThread().name}, ${responseData.humidity}")
       // return responseData
    }
}