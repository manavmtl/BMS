package com.bookmyshow.network.provider

import android.content.Context
import com.bookmyshow.core.NetworkManager
import com.bookmyshow.network.utils.ConnectivityInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class NetworkProviderImpl @Inject constructor(
    private val context: Context,
    private val networkManager: NetworkManager
) : com.bookmyshow.core.NetworkProvider {

    private companion object {
        const val CONNECT_TIMEOUT = 30L
        const val READ_TIMEOUT = 30L
    }

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor(context,networkManager))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()

    override fun <ApiClass : Any> getApi(
        apiClass: Class<ApiClass>,
        baseUrl: String
    ): ApiClass {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiClass) as ApiClass
    }

}