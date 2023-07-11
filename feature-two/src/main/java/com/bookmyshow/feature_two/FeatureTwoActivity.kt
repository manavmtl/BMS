package com.bookmyshow.feature_two

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
        setUpToolbar()
        if(intent.hasExtra("key_data")){
            val jsonObject=JSONObject(intent?.extras?.getString("key_data","")?:"")
            updateUI(jsonObject)
        }
    }

    private fun setUpToolbar() {
        supportActionBar?.title=getString(R.string.movie_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return false
    }

    private fun updateUI(jsonObject: JSONObject) {
        val venueName=jsonObject.getString("parent_venue_item")?:""
        val timeSelected=jsonObject.getString("time_selected")?:""

        binding.apply {
            tvVenueName.text=venueName
            tvTime.text=timeSelected
        }
    }
}