package com.example.samplefoodapp.domain.network.repository

import com.example.samplefoodapp.domain.model.ReceipeList

interface RecipeRepository {
    suspend fun search(token: String, page: Int, query: String): List< ReceipeList>

    suspend fun get(token: String, id: Int): ReceipeList
}