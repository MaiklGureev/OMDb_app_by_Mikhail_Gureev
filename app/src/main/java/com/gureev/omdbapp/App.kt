package com.gureev.omdbapp

import android.app.Application
import com.gureev.omdbapp.utils.di.AppComponent
import com.gureev.omdbapp.utils.di.DaggerAppComponent
import com.gureev.omdbapp.utils.di.modules.AppModule

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger2()
    }

    private fun initDagger2() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
    }

}