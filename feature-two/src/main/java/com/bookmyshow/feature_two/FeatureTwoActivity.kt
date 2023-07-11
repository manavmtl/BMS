package com.bookmyshow.feature_two

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bookmyshow.feature_two.databinding.ActivityFeatureTwoBinding
import org.json.JSONObject

class FeatureTwoActivity : AppCompatActivity() {
private lateinit var binding:ActivityFeatureTwoBinding
private val TAG=FeatureTwoActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feature_two)

        if(intent.hasExtra("key_data")){
            val jsonObject=JSONObject(intent?.extras?.getString("key_data","")?:"")
            Log.d(TAG, "onCreate: $jsonObject")
        }
    }
}