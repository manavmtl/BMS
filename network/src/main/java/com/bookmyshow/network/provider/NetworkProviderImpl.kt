package com.bookmyshow.network.provider

import android.content.Context
import com.bookmyshow.core.NetworkManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class NetworkProviderImpl : com.bookmyshow.core.NetworkProvider {

    private companion object {
        const val CONNECT_TIMEOUT = 30L
        const val READ_TIMEOUT = 30L
    }

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//            .addInterceptor(ConnectivityInterceptor())
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

  /* private inner class ConnectivityInterceptor () : Interceptor {
       @Inject
       lateinit var networkManager: NetworkManager
       override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
           if (!networkManager.isNetworkConnected) {
               throw IOException("No internet connection")
           }

           return chain.proceed(chain.request())
       }
   }*/
}