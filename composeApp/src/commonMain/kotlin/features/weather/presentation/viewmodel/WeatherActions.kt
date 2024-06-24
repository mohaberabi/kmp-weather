package features.weather.presentation.viewmodel

sealed interface WeatherActions {


    data object OnLocationGranted : WeatherActions
    
}