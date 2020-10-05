package com.example.sunnyday.repository

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL="https://api.openweathermap.org/"
private const val API_KEY="YOUR_API_KEY"

interface WeatherService {

    @GET("/data/2.5/weather?appid=${API_KEY}&units=metric")
    fun getWeather(@Query("q") cityName: String): Call<ModelClass>

    companion object{

        fun create(): WeatherService{
            val retrofit= Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(WeatherService::class.java)
        }
    }

}