package com.bookmyshow.feature_one.repository

import android.util.Log
import com.bookmyshow.core.NetworkProvider
import com.bookmyshow.feature_one.model.MovieListResponse
import com.bookmyshow.feature_one.networkState.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Akshansh Dhing on 12/10/22.
 */
class ShowTimesRepository @Inject constructor(
    private val networkProvider: NetworkProvider
) {

    private val api: ShowTimesAPI
        get() = networkProvider.getApi(
            apiClass = ShowTimesAPI::class.java,
            baseUrl = "https://demo2782755.mockable.io/"
        )

    suspend fun getMoviesList(): Flow<ApiState<MovieListResponse>> {
        return flow {
            val response = api.getShowTimes()
            emit(ApiState.success(response))
        }.flowOn(Dispatchers.IO)
    }
}