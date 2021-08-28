package com.gureev.omdbapp

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.gureev.omdbapp.utils.di.AppComponent
import com.gureev.omdbapp.utils.di.DaggerAppComponent
import com.gureev.omdbapp.utils.di.modules.AppModule

class App : MultiDexApplication() {

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

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}