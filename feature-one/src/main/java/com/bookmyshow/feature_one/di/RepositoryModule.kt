package com.bookmyshow.feature_one.di

import com.bookmyshow.core.NetworkProvider
import com.bookmyshow.feature_one.repository.ShowTimesRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideMyRepository (networkProvider: NetworkProvider): ShowTimesRepository {
        return ShowTimesRepository(networkProvider)
    }
}
