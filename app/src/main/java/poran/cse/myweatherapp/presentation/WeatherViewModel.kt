package poran.cse.myweatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import poran.cse.myweatherapp.domain.location.LocationTracker
import poran.cse.myweatherapp.domain.repository.WeatherRepository
import poran.cse.myweatherapp.domain.util.Resource
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor (
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel()  {

    var state by mutableStateOf(WeatherState())
    private set

    fun weatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                currentWeatherInfo = null,
                error = null
            )
            
            locationTracker.getCurrentLocation()?.let {  location ->  
                when(val result = repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            currentWeatherInfo = result.data,
                            error = null
                        )

                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = result.message,
                            currentWeatherInfo = null
                        )
                    }
                }
            }
                ?: kotlin.run {
                    state = state.copy(
                        currentWeatherInfo = null,
                        isLoading = false,
                        error = "Couldn't retrieve location. Make sure to grant permission and enable gps service."
                    )
                }
        }
    }

}