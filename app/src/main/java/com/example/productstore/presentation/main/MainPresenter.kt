package com.example.productstore.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.productstore.App
import com.example.productstore.R
import com.example.productstore.other.cicerone.Screens
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

@InjectViewState
class MainPresenter : MvpPresenter<MvpView>() {

    override fun onFirstViewAttach() {
        App.router.navigateTo(Screens.ProductListScreen())
    }

    fun getNavigator(
        activity: FragmentActivity,
        fragmentManager: FragmentManager,
        containerId: Int
    ): SupportAppNavigator = object : SupportAppNavigator(activity, fragmentManager, containerId) {
        override fun fragmentBack() = if (fragmentManager.backStackEntryCount > 1) fragmentManager.popBackStack() else activityBack()

        override fun setupFragmentTransaction(command: Command?, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction?) {
            super.setupFragmentTransaction(command, currentFragment, nextFragment, fragmentTransaction)
            when (command) {
                is Forward -> fragmentTransaction?.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            }
        }
    }

    fun onBackPressed() {
        App.router.exit()
    }
}