package com.example.sunnyday.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunnyday.repository.MainRepository
import com.example.sunnyday.repository.RequiredData
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val repository= MainRepository()
    val tempData= MutableLiveData<RequiredData>()
    val cityName= MutableLiveData("New Delhi")

    fun getWeatherFromRepo(cityName: String="New Delhi"){
        Log.d("ViewModel","In ${Thread.currentThread().name}")

        viewModelScope.launch{
            Log.d("ViewModel","In viewModelScope ${Thread.currentThread().name}")
            val response: RequiredData =repository.callApi(cityName)
            Log.d("ApiCall", response.feelsLike)
            tempData.value= response
        }
    }

    fun updateCityName(cityInput: String){
        cityName.value= cityInput
    }

}