package com.bookmyshow.feature_one

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bookmyshow.core.DialogLoader
import com.bookmyshow.core.NoConnectivityException
import com.bookmyshow.core.ToastLoader
import com.bookmyshow.feature_one.adapters.AdapterFilterDate
import com.bookmyshow.feature_one.adapters.AdapterMovieList
import com.bookmyshow.feature_one.databinding.ActivityFeatureOneBinding
import com.bookmyshow.feature_one.di.FeatureOneDaggerProvider
import com.bookmyshow.feature_one.model.MovieListResponse
import com.bookmyshow.feature_one.networkState.Status
import com.bookmyshow.feature_one.utils.gone
import com.bookmyshow.feature_one.utils.visible
import com.bookmyshow.feature_one.viewmodel.FeatureOneViewModel
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class FeatureOneActivity : AppCompatActivity() {
    private val TAG: String = FeatureOneActivity::class.java.simpleName

    @Inject
    lateinit var toastLoader: ToastLoader
    @Inject
    lateinit var dialogLoader:DialogLoader
    @Inject
    lateinit var viewModel: FeatureOneViewModel
    private var moviesAdapter: AdapterMovieList? = null
    private var dateAdapter: AdapterFilterDate? = null
    private lateinit var binding: ActivityFeatureOneBinding
    private var venuesList = ArrayList<MovieListResponse.Venue>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FeatureOneDaggerProvider.component.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feature_one)

        setUpToolbar()
        initMoviesListAdapter()
        initDateAdapter()
        setObservers()
        getMoviesData()
    }

    private fun setUpToolbar() {
        supportActionBar?.title=getString(R.string.movies_list)
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

    private fun initDateAdapter() {
        dateAdapter = AdapterFilterDate { position, venue ->
            filterVenueList(venue, position)
        }
        binding.rvFiltersDate.adapter = dateAdapter
    }

    private fun filterVenueList(venue: MovieListResponse.Venue, position: Int) {
        val isSelected = venue.isSelected
        if (isSelected) {
            val filteredList = venuesList.filter { it.showDate == venue.showDate } as ArrayList<MovieListResponse.Venue>
            moviesAdapter?.updateData(filteredList)
        }else{
            moviesAdapter?.updateData(venuesList)
        }
    }

    private fun initMoviesListAdapter() {
        moviesAdapter = AdapterMovieList { parentPosition: Int,
                                           childPosition: Int,
                                           parentVenueItem: MovieListResponse.Venue,
                                           childTimeItem: MovieListResponse.Venue.Showtime ->

            moveDataToNextScreen(parentVenueItem,childTimeItem)
        }
        binding.rvMoviesList.adapter = moviesAdapter
    }

    private fun moveDataToNextScreen(
        parentVenueItem: MovieListResponse.Venue,
        childTimeItem: MovieListResponse.Venue.Showtime
    ) {
        val jsonObject=JSONObject()
        jsonObject.put(TIME_SELECTED,childTimeItem.showTime)
        jsonObject.put(PARENT_VENUE_ITEM,parentVenueItem.name)

        val intent=Intent(ACTION_TIME_CLICK)
        intent.putExtra(KEY_DATA,jsonObject.toString())
        sendBroadcast(intent)
    }

    private fun getMoviesData() {
        viewModel.getMoviesData()
    }

    private fun setObservers() {
        viewModel.moviesData.observe(this) {
            Log.d(TAG, "setObservers: $it")
            when (it.status) {
                Status.SUCCESS -> {
                    hideLoader()
                    it?.data?.venues?.let { venues ->
                        venuesList = venues
                        dateAdapter?.updateData(venuesList)
                        moviesAdapter?.updateData(venues)
                    }
                }

                Status.LOADING->{
                    showLoader()
                }
                else -> {
                    hideLoader()
                    checkErrorType(it.errorModel)
                }
            }
        }
    }

    private fun checkErrorType(it: Throwable?) {
        if (it is NoConnectivityException) {
            dialogLoader.noInternetDialog(this@FeatureOneActivity)
        }else{
            toastLoader.showToastShort(this,getString(R.string.something_went_wrong))
        }
    }

    private fun showLoader(){
        binding.apply {
            progressCircular.visible()
            rvMoviesList.gone()
            cardDatesFilter.gone()
        }
    }

    private fun hideLoader(){
        binding.apply {
            progressCircular.gone()
            rvMoviesList.visible()
            cardDatesFilter.visible()
        }
    }

    companion object{
        const val ACTION_TIME_CLICK="com.bookmyshow.feature_one.action_time_click"
        const val TIME_SELECTED="time_selected"
        const val PARENT_VENUE_ITEM="parent_venue_item"
        const val KEY_DATA="key_data"
    }
}