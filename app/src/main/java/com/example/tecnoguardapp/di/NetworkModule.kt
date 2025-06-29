package com.example.tecnoguardapp.di

import com.example.tecnoguardapp.data.network.TecnoGuardApiClient
import com.example.tecnoguardapp.data.network.TecnoGuardAuthClient
import com.example.tecnoguardapp.utils.Constants.API
import com.example.tecnoguardapp.utils.Constants.URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthNamedRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("api")
    fun provideApiNamedRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthApi(@Named("auth") retrofit: Retrofit): TecnoGuardAuthClient {
        return retrofit.create(TecnoGuardAuthClient::class.java)
    }

    @Provides
    @Singleton
    @Named("api")
    fun provideApiService(@Named("api") retrofit: Retrofit): TecnoGuardApiClient {
        return retrofit.create(TecnoGuardApiClient::class.java)
    }
}