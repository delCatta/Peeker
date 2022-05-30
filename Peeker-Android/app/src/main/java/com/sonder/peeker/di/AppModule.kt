package com.sonder.peeker.di

import android.content.Context
import com.sonder.peeker.core.Constants
import com.sonder.peeker.data.remote.PeekerApi
import com.sonder.peeker.domain.repository.PeekerRepository
import com.sonder.peeker.domain.repository.PeekerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePeekerApi(@ApplicationContext context: Context): PeekerApi {
        return Retrofit.Builder().baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient(context))
            .build()
            .create(PeekerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDocumentRepository(api: PeekerApi): PeekerRepository {
        return PeekerRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    private fun okhttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }
}