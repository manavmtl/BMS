package com.bookmyshow.network.utils

import android.content.Context
import com.bookmyshow.core.NetworkManager
import com.bookmyshow.core.NoConnectivityException
import com.bookmyshow.network.R
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor (private val context: Context, private val networkManager: NetworkManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkManager.isNetworkConnected) {
            throw NoConnectivityException(context.getString(R.string.no_internet_connection))
        }
        return chain.proceed(chain.request())
    }
}
