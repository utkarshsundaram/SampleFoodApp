package com.example.samplefoodapp.presentation.ui.application

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp
import android.net.NetworkInfo

import android.net.ConnectivityManager




@HiltAndroidApp
class BaseApplication:Application() {

    val isDark = mutableStateOf(false)

    fun toggleLightTheme(){
        isDark.value = false
    }
    fun toggleDarkTheme(){
        isDark.value=true
    }

     fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}