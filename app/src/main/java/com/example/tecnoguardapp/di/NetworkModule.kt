package com.example.tecnoguardapp.di

import com.example.tecnoguardapp.data.network.BusinessApiClient
import com.example.tecnoguardapp.data.network.TecnoGuardAuthApiClient
import com.example.tecnoguardapp.data.network.TecnoGuardAuthClient
import com.example.tecnoguardapp.utils.ErrorManager
import com.example.tecnoguardapp.utils.Constants.API
import com.example.tecnoguardapp.utils.Constants.BUSINESS_API
import com.example.tecnoguardapp.utils.Constants.URL
import com.example.tecnoguardapp.utils.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        dataStoreManager: DataStoreManager,
        errorManager: ErrorManager
    ): AuthInterceptor {
        return AuthInterceptor(dataStoreManager, errorManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthNamedRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("api")
    fun provideApiNamedRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(API)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("business")
    fun provideBusinessNamedRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BUSINESS_API)
        .client(okHttpClient)
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