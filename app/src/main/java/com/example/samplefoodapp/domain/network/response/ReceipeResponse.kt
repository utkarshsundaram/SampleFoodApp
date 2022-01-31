package com.example.samplefoodapp.domain.network.response

import android.os.Parcelable
import com.example.samplefoodapp.domain.model.ReceipeList
import com.example.samplefoodapp.domain.network.model.ReceipeDTOMapper
import com.example.samplefoodapp.domain.network.model.RecipeDTO
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReceipeResponse (@SerializedName("count")var count:Int,
                            @SerializedName("previous")var previous:String,
                            @SerializedName("next")var next:String,
                            @SerializedName("results")var result:ArrayList<RecipeDTO>):
    Parcelable {
}