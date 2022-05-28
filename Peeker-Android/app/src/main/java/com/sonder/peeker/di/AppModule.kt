package com.sonder.peeker.di

import com.sonder.peeker.core.Constants
import com.sonder.peeker.data.remote.PeekerApi
import com.sonder.peeker.domain.repository.PeekerRepository
import com.sonder.peeker.domain.repository.PeekerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePeekerApi(): PeekerApi {
        return Retrofit.Builder().baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PeekerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDocumentRepository(api: PeekerApi): PeekerRepository {
        return PeekerRepositoryImpl(api)
    }
}