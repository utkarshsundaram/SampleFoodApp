package com.example.samplefoodapp.presentation.ui.receipelist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplefoodapp.commonutils.Constant.PAGE_SIZE
import com.example.samplefoodapp.commonutils.Constant.TAG
import com.example.samplefoodapp.commonutils.FoodCategories
import com.example.samplefoodapp.commonutils.getFoodCategory
import com.example.samplefoodapp.domain.model.ReceipeList
import com.example.samplefoodapp.domain.network.repository.RecipeRepository
import com.example.samplefoodapp.presentation.ui.application.BaseApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ReceipeListViewModel @Inject constructor(val mApplication: BaseApplication,private  val repository: RecipeRepository,
                                               @Named("auth_token")private val token:String):ViewModel() {



    val recipes: MutableState<List<ReceipeList>> = mutableStateOf(ArrayList())
    val queries:MutableState<String> = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategories?> = mutableStateOf(null)
    var categoriesScrollPosition:Float= 0.0F
    var loadingProgressBar= mutableStateOf(false)
    var pageNo:Int=1
    val showSnackBar= mutableStateOf(false)
    val receipeId= mutableStateOf(1)

    init {
        newSearch()
    }
    fun newSearch() {
        if (mApplication.isNetworkAvailable()) {
            viewModelScope.launch {
                loadingProgressBar.value = true
                showSnackBar.value=true
                delay(1000)
                resetSearchState()
                val result = repository.search(
                    token = token,
                    page = pageNo,
                    query = queries.value
                )
                recipes.value = result
                loadingProgressBar.value = false
                Log.d(TAG, recipes.toString())
            }
        }else{
            loadingProgressBar.value = false
            showSnackBar.value=true

        }
    }

    fun nextPage() {
        if (mApplication.isNetworkAvailable()) {
            viewModelScope.launch {
                if ((categoriesScrollPosition + 1) >= (pageNo * PAGE_SIZE)) {
                    loadingProgressBar.value = true
                    incrementPageNo()
                    delay(1000)
                    if (pageNo > 1) {
                        val result = repository.search(
                            token = token,
                            page = pageNo,
                            query = queries.value
                        )
                        Log.d(TAG, "next page:{$result}")
                        appendCurrentList(result)
                        loadingProgressBar.value = false

                    }
                }
            }
        }else{
            loadingProgressBar.value = false
            showSnackBar.value=true

        }
    }
    private fun resetSearchState(){
        recipes.value = listOf()
        pageNo=1;
        onChangedCategoryScrollPosition(0f)
        if(selectedCategory.value?.value != queries.value) clearSelectedCategory()
    }
    fun incrementPageNo(){
        pageNo++
    }
    fun appendCurrentList(receipeList: List<ReceipeList>){
        var currentList=ArrayList(recipes.value)
        currentList.addAll(receipeList)
        recipes.value=currentList
    }
    private fun clearSelectedCategory(){
        selectedCategory.value = null
    }
    fun onQueriesChanged(text:String){
        queries.value=text
    }
    fun onQueryChanged(query: String){
        this.queries.value = query
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangedCategoryScrollPosition(value:Float){
        categoriesScrollPosition=value
    }



}