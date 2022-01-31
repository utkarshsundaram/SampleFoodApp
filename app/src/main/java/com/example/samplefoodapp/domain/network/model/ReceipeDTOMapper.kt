package com.example.samplefoodapp.domain.network.model

import com.example.samplefoodapp.domain.model.ReceipeList
import com.example.samplefoodapp.domain.network.response.ReceipeResponse
import com.example.samplefoodapp.domain.util.DomainMapper

class ReceipeDTOMapper : DomainMapper<RecipeDTO, ReceipeList> {
    override fun mapToDomainModel(domainModel: ReceipeList): RecipeDTO {
        return RecipeDTO(
            pk = domainModel.id,
            title = domainModel.title,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            publisher = domainModel.publisher,
            sourceUrl = domainModel.sourceUrl,
            description = domainModel.description,
            cookingInstructions = domainModel.cookingInstructions,
            ingredients = domainModel.ingredients,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated,
        )
    }

    override fun mapFromDomainModel(entity: RecipeDTO): ReceipeList {
        return ReceipeList(
            id = entity.pk,
            title = entity.title,
            featuredImage = entity.featuredImage,
            rating = entity.rating,
            publisher = entity.publisher,
            sourceUrl = entity.sourceUrl,
            description = entity.description,
            cookingInstructions = entity.cookingInstructions,
            ingredients = entity.ingredients?: listOf(),
            dateAdded = entity.dateAdded,
            dateUpdated = entity.dateUpdated,
        )
    }
    fun fromEntityList(initial: List<RecipeDTO>): List<ReceipeList>{
        return initial.map { mapFromDomainModel(it) }
    }

    fun toEntityList(initial: List<ReceipeList>): List<RecipeDTO>{
        return initial.map { mapToDomainModel(it) }
    }



}