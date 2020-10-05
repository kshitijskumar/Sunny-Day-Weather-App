package com.example.sunnyday.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.sunnyday.R
import com.example.sunnyday.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.cityName.observe(this,{
            progressBar.visibility= View.VISIBLE
            showData(it)
            cityNameView.setText(it)
        })

        cityNameBtn.setOnClickListener{
            if(cityNameView.text.isEmpty()){
                Toast.makeText(this,"City name box can't be empty", Toast.LENGTH_SHORT).show()
                cityNameView.setText(viewModel.cityName.value)
            }else{
                viewModel.updateCityName(cityNameView.text.toString())
                progressBar.visibility= View.VISIBLE
                showData(cityNameView.text.toString())
            }
        }

    }


    @SuppressLint("SetTextI18n")
    private fun showData(cityName: String= "New Delhi"){

        viewModel.getWeatherFromRepo(cityName)
        viewModel.tempData.observe(this, {
            progressBar.visibility= View.GONE
            if(it.status=="Success") {
                Log.d("Main Activity", it.feelsLike)
                weatherView.text = it.weatherType
                when (it.weatherType) {
                    "Thunderstorm" -> weatherIcon.setImageResource(R.drawable.thunder_storm)
                    "Drizzle" -> weatherIcon.setImageResource(R.drawable.shower_rain)
                    "Rain" -> weatherIcon.setImageResource(R.drawable.rain)
                    "Snow" -> weatherIcon.setImageResource(R.drawable.snow)
                    "Mist", "Haze", "Smoke", "Dust", "Sand", "Fog", "Ash", "Squall", "Tornado" -> weatherIcon.setImageResource(
                        R.drawable.mist
                    )
                    "Clear" -> weatherIcon.setImageResource(R.drawable.clear_sky)
                    "Clouds" -> weatherIcon.setImageResource(R.drawable.few_clouds)
                }
                currentTemp.text = it.temp
                minTemp.text = it.minTemp
                maxTemp.text = it.maxTemp
                feelsLikeTemp.text = "Feels like ${it.feelsLike}"
                pressureView.text = "Pressure ${it.pressure}"
                humidityView.text = "Humidity ${it.humidity}"
                windSpdView.text = "Wind Speed ${it.windSpeed}"
                visibilityView.text = "Visibility ${it.visibility}"
            }else{
                Log.d("Show data func","Else block with ${it.weatherType}")
                Toast.makeText(this,"Check your network connection or country name", Toast.LENGTH_SHORT).show()
            }

        })

    }
}