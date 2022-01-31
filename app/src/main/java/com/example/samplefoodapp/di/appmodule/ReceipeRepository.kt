package com.example.samplefoodapp.di.appmodule

import com.example.samplefoodapp.domain.network.model.ReceipeDTOMapper
import com.example.samplefoodapp.domain.network.repository.RecipeRepository
import com.example.samplefoodapp.domain.network.repository.RecipeRepository_Impl
import com.example.samplefoodapp.domain.network.service.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReceipeRepository {

    @Singleton
    @Provides
    fun provideReceipeRepository(service: RetrofitService,mapper:ReceipeDTOMapper): RecipeRepository {
        return RecipeRepository_Impl(service, mapper = mapper)
    }
}