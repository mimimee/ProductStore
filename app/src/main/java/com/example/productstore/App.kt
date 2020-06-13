package com.example.productstore

import android.app.Application
import android.content.Context
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        cicerone = Cicerone.create()
    }

    companion object {
        lateinit var INSTANCE: App
        private lateinit var cicerone: Cicerone<Router>

        val context: Context
            get() = INSTANCE.applicationContext

        val router: Router
            get() = cicerone.router

        fun setNavigator(navigator: Navigator) = cicerone.navigatorHolder.setNavigator(navigator)
        fun removeNavigator() = cicerone.navigatorHolder.removeNavigator()
    }
}