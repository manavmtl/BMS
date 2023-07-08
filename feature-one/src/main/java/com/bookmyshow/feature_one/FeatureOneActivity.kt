package com.bookmyshow.feature_one

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bookmyshow.feature_one.adapters.AdapterFilterDate
import com.bookmyshow.feature_one.adapters.AdapterMovieList
import com.bookmyshow.feature_one.databinding.ActivityFeatureOneBinding
import com.bookmyshow.feature_one.di.FeatureOneDaggerProvider
import com.bookmyshow.feature_one.model.MovieListResponse
import com.bookmyshow.feature_one.networkState.Status
import com.bookmyshow.feature_one.viewmodel.FeatureOneViewModel
import javax.inject.Inject

class FeatureOneActivity : AppCompatActivity() {
    private val TAG: String = FeatureOneActivity::class.java.simpleName

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

        initMoviesListAdapter()
        initDateAdapter()
        setObservers()
        getMoviesData()
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

        //TODO move to next screen
    }

    private fun getMoviesData() {
        viewModel.getMoviesData()
    }

    private fun setObservers() {
        viewModel.moviesData.observe(this) {
            Log.d(TAG, "setObservers: $it")
            when (it.status) {
                Status.SUCCESS -> {
                    it?.data?.venues?.let { venues ->
                        venuesList = venues
                        dateAdapter?.updateData(venuesList)
                        moviesAdapter?.updateData(venues)
                    }
                }

                Status.LOADING->{

                }
                else -> {
                    Unit
                }
            }
        }
    }
}