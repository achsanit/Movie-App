package com.achsanit.movieapp.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.achsanit.movieapp.di.KoinInitializer

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        disableDarkTheme()
        KoinInitializer.init(this)
    }

    private fun disableDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}