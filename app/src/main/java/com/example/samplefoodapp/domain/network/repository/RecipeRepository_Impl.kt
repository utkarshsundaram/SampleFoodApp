package com.example.samplefoodapp.domain.network.repository

import com.example.samplefoodapp.domain.model.ReceipeList
import com.example.samplefoodapp.domain.network.model.ReceipeDTOMapper
import com.example.samplefoodapp.domain.network.model.RecipeDTO
import com.example.samplefoodapp.domain.network.service.RetrofitService

class RecipeRepository_Impl (private val recipeService: RetrofitService,
private val mapper: ReceipeDTOMapper,
): RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<ReceipeList> {
        val response = recipeService.search(token = token, page, query = query)
        if (response.isSuccessful) {
            return recipeService.search(
                token = token,
                page,
                query = query
            ).body()?.result?.let {
                mapper.fromEntityList(
                    it
                )
            }!!
        }else{
            return mapper.fromEntityList(arrayListOf())
        }
    }

    override suspend fun get(token: String, id: Int): ReceipeList {
        val response=recipeService.get(token = token, id)
        if(response.isSuccessful){
            return recipeService.get(token = token, id).body()
                ?.let { mapper.mapFromDomainModel(it) }!!

        }else{
            return  mapper.mapFromDomainModel(RecipeDTO(1,
                "","","",0,"","",
                "", listOf(),"",""))
        }
    }
}