package com.bookmyshow.feature_one.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookmyshow.feature_one.model.MovieListResponse
import com.bookmyshow.feature_one.networkState.ApiState
import com.bookmyshow.feature_one.repository.ShowTimesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Akshansh Dhing on 12/10/22.
 */
class FeatureOneViewModel @Inject constructor(private val repo: ShowTimesRepository) : ViewModel() {
    private val _moviesData = MutableLiveData<ApiState<MovieListResponse>>()
    val moviesData: LiveData<ApiState<MovieListResponse>>
        get() = _moviesData

    fun getMoviesData() = viewModelScope.launch {
        repo.getMoviesList().onStart {
            _moviesData.value = ApiState.loading()
        }.catch {
            _moviesData.value = ApiState.error("Something went wrong")
        }.collect {
            _moviesData.value = ApiState.success(it.data)
        }
    }

}