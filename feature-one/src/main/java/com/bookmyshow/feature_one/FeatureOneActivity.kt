package com.bookmyshow.feature_one

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bookmyshow.feature_one.adapters.AdapterMovieList
import com.bookmyshow.feature_one.databinding.ActivityFeatureOneBinding
import com.bookmyshow.feature_one.di.FeatureOneDaggerProvider
import com.bookmyshow.feature_one.model.MovieListResponse
import com.bookmyshow.feature_one.networkState.Status
import com.bookmyshow.feature_one.viewmodel.FeatureOneViewModel
import javax.inject.Inject

class FeatureOneActivity : AppCompatActivity() {
    private val TAG:String=FeatureOneActivity::class.java.simpleName
    @Inject
    lateinit var viewModel: FeatureOneViewModel
    private var moviesAdapter:AdapterMovieList?=null
    private lateinit var binding: ActivityFeatureOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FeatureOneDaggerProvider.component.inject(this)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_feature_one)

        setUpToolbar()
        initAdapter()
        setObservers()
        getMoviesData()
    }

    private fun setUpToolbar() {
        binding.btnFilter.setOnClickListener {
            openFilters()
        }
    }

    private fun openFilters() {

    }

    private fun initAdapter() {
        moviesAdapter= AdapterMovieList{ parentPosition: Int,
                                         childPosition: Int,
                                         parentVenueItem: MovieListResponse.Venue,
                                         childTimeItem: MovieListResponse.Venue.Showtime ->

        }
        binding.rvMoviesList.adapter=moviesAdapter
    }

    private fun getMoviesData() {
        viewModel.getMoviesData()
    }

    private fun setObservers() {
        viewModel.moviesData.observe(this) {
            Log.d(TAG, "setObservers: $it")
            when (it.status) {
                Status.SUCCESS->{

                    it?.data?.venues?.let { venues->
                        moviesAdapter?.updateData(venues)
                    }
                }else->{

                }
            }
        }
    }
}