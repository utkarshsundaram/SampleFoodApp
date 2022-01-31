package com.example.samplefoodapp.di.appmodule

import android.app.Application
import android.content.Context
import com.example.samplefoodapp.domain.network.model.ReceipeDTOMapper
import com.example.samplefoodapp.domain.network.service.RetrofitService
import com.example.samplefoodapp.presentation.ui.application.BaseApplication
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

import okhttp3.logging.HttpLoggingInterceptor


import java.io.IOException


@Module
@InstallIn(SingletonComponent::class)
object NetworkMapper {

   fun getClient():OkHttpClient{
       val httpClient: OkHttpClient =
          OkHttpClient().newBuilder()//here we can add Interceptor for dynamical adding headers
               .addNetworkInterceptor(object : Interceptor {
                   @Throws(IOException::class)
                   override fun intercept(chain: Interceptor.Chain): Response {
                       val request: Request =
                           chain.request().newBuilder().addHeader("test", "test").build()
                       return chain.proceed(request)
                   }
               }) //here we adding Interceptor for full level logging
               .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
               .build()
       return  httpClient
   }
    @Singleton
    @Provides
    fun providesRetrofitService(): RetrofitService {
            return Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/recipe/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(getClient())
            .build()
            .create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideReceipeDTO():ReceipeDTOMapper{
        return ReceipeDTOMapper()
    }

    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken(): String{
        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }
}