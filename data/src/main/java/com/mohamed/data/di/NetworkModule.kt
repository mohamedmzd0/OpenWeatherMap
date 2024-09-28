package com.mohamed.data.di

import com.google.gson.Gson
import com.mohamed.core.utils.GlobalErrorHandler
import com.mohamed.data.BuildConfig
import com.mohamed.data.remote.CitiesApi
import com.mohamed.data.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    // log interceptor
    @Provides
    @Singleton
    fun provideLogInterceptor(): okhttp3.logging.HttpLoggingInterceptor {
        return okhttp3.logging.HttpLoggingInterceptor()
    }

    @Provides
    @Singleton
    fun provideErrorHandlingCallAdapterFactory(): com.mohamed.core.utils.ErrorHandlingConverterFactory {
        return com.mohamed.core.utils.ErrorHandlingConverterFactory()
    }

    @Provides
    @Singleton
    fun provideOkHttp(interceptor: okhttp3.logging.HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
        return builder.build()
    }


    @Provides
    @Singleton
    fun provideGsonConvertFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(Gson())
    }


    @Provides
    @Singleton
    fun provideGlobalErrorHandler(): GlobalErrorHandler {
        return GlobalErrorHandler()
    }



    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        errorHandlingConverterFactory: com.mohamed.core.utils.ErrorHandlingConverterFactory
    ): Retrofit {
        return Retrofit.Builder().client(okHttpClient).addConverterFactory(gsonConverterFactory)
            .addConverterFactory(errorHandlingConverterFactory)
            .baseUrl(BuildConfig.BASE_URL).build()
    }


    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCitiesApi(retrofit: Retrofit): CitiesApi {
        return retrofit.create(CitiesApi::class.java)
    }

}