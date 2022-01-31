package com.example.samplefoodapp.domain.network.service

import com.example.samplefoodapp.domain.network.model.RecipeDTO
import com.example.samplefoodapp.domain.network.response.ReceipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitService {

    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<ReceipeResponse>

    @GET("get")
    suspend fun get(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Response<RecipeDTO>
}