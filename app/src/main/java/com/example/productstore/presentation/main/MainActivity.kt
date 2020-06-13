package com.example.productstore.presentation.main

import android.os.Bundle
import com.example.productstore.App
import com.example.productstore.R
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        App.setNavigator(presenter.getNavigator(this, supportFragmentManager, R.id.main_container))
    }

    override fun onPause() {
        super.onPause()
        App.removeNavigator()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}
