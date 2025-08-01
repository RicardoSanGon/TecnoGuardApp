package com.example.tecnoguardapp.di

import com.example.tecnoguardapp.data.network.BusinessApiClient
import com.example.tecnoguardapp.data.network.TecnoGuardAuthApiClient
import com.example.tecnoguardapp.data.network.TecnoGuardAuthClient
import com.example.tecnoguardapp.utils.Constants.API
import com.example.tecnoguardapp.utils.Constants.BUSINESS_API
import com.example.tecnoguardapp.utils.Constants.BUSINESS_URL
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
    @Named("business")
    fun provideBusinessNamedRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BUSINESS_API)
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
    @Named("business")
    fun provideBusinessApi(@Named("business") retrofit: Retrofit): BusinessApiClient {
        return retrofit.create(BusinessApiClient::class.java)
    }

    @Provides
    @Singleton
    @Named("api")
    fun provideApiService(@Named("api") retrofit: Retrofit): TecnoGuardAuthApiClient {
        return retrofit.create(TecnoGuardAuthApiClient::class.java)
    }
}