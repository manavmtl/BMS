package com.bookmyshow.assignment

import android.app.Application
import android.content.IntentFilter
import com.bookmyshow.assignment.di.AppComponent
import com.bookmyshow.assignment.di.DaggerAppComponent
import com.bookmyshow.assignment.di.DaggerAppComponentProvider
import com.bookmyshow.assignment.utils.ClickReceiver
import com.bookmyshow.core.di.CoreComponentProvider
import com.bookmyshow.feature_one.FeatureOneActivity

class AssignmentApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val appComponent: AppComponent = DaggerAppComponent.factory().create(this)
        DaggerAppComponentProvider.setAppComponent(appComponent)
        CoreComponentProvider.coreComponent = appComponent

        registerAppBroadcastReceiver()
    }

    private fun registerAppBroadcastReceiver() {
        val intentFilter = filtersIntent
        registerReceiver(appBroadcastReceiver, intentFilter)
    }

    private fun unregisterAppBroadcastReceiver() {
        unregisterReceiver(appBroadcastReceiver)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterAppBroadcastReceiver()
    }


    private val appBroadcastReceiver= ClickReceiver()

    private val filtersIntent= IntentFilter().apply {
        addAction(FeatureOneActivity.ACTION_TIME_CLICK)
    }
}