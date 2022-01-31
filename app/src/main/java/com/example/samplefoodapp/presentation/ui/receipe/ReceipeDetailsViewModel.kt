package com.example.samplefoodapp.presentation.ui.receipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplefoodapp.commonutils.FoodCategories
import com.example.samplefoodapp.domain.model.ReceipeList
import com.example.samplefoodapp.domain.network.repository.RecipeRepository
import com.example.samplefoodapp.presentation.ui.application.BaseApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ReceipeDetailsViewModel@Inject constructor(private  val repository: RecipeRepository,
                                                 @Named("auth_token")private val token:String):
    ViewModel()
{
    @Inject
    lateinit var mApplication: BaseApplication

    var receipeId= mutableStateOf(1)

    val recipes: MutableState<ReceipeList?> = mutableStateOf(null)

    val showSnackBar= mutableStateOf(false)

    fun getReceipeById() {
        if (mApplication.isNetworkAvailable()) {
            showSnackBar.value=false
            viewModelScope.launch {
                val result = repository.get(token, receipeId.value)
                recipes.value = result
            }
        }else{
            showSnackBar.value=true
        }
    }

    fun setReceipeId(Id:Int){
        receipeId.value=Id
    }
}