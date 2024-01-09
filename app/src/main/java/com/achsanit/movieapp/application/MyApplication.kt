package com.achsanit.movieapp.application

import android.app.Application
import com.achsanit.movieapp.di.KoinInitializer

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        KoinInitializer.init(this)
    }
}