package com.bookmyshow.assignment.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bookmyshow.feature_one.FeatureOneActivity
import com.bookmyshow.feature_two.FeatureTwoActivity

class ClickReceiver:BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent?) {
        when (p1?.action) {
            FeatureOneActivity.ACTION_TIME_CLICK->{
                val intent = Intent(p0, FeatureTwoActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtras(p1)
                p0.startActivity(intent)            }
        }
    }
}