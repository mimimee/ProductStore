package com.example.productstore

import android.app.Application
import android.content.Context
import com.example.productstore.data.db.ProductsDataBase
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        cicerone = Cicerone.create()
        dataBase = ProductsDataBase.getInstance()
    }

    companion object {
        lateinit var INSTANCE: App
        lateinit var dataBase: ProductsDataBase
        private lateinit var cicerone: Cicerone<Router>

        val context: Context
            get() = INSTANCE.applicationContext

        val router: Router
            get() = cicerone.router

        fun setNavigator(navigator: Navigator) = cicerone.navigatorHolder.setNavigator(navigator)
        fun removeNavigator() = cicerone.navigatorHolder.removeNavigator()
    }
}