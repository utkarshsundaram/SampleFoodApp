package com.example.samplefoodapp.presentation.ui.receipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.samplefoodapp.commonutils.Constant.RECEIPE_ID
import com.example.samplefoodapp.commonutils.Constant.TAG
import com.example.samplefoodapp.domain.model.ReceipeList
import com.example.samplefoodapp.presentation.ui.application.BaseApplication
import com.example.samplefoodapp.presentation.ui.component.CircularProgressionBar
import com.example.samplefoodapp.presentation.ui.component.ReceipeDetailScreen
import com.example.samplefoodapp.presentation.ui.component.SnackbarController
import com.example.samplefoodapp.presentation.ui.receipelist.ReceipeListViewModel
import com.example.samplefoodapp.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReceipeDetailFragment:Fragment() {
    private val viewModel: ReceipeDetailsViewModel by activityViewModels<ReceipeDetailsViewModel>()
    @ExperimentalMaterialApi
    private val snackbarController = SnackbarController(lifecycleScope)
    @Inject
    lateinit var mApplication: BaseApplication

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = ComposeView(requireContext())
        view.apply {
            setContent {
                DefaultPreview()
            }
        }

        return view
    }
    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        AppTheme(darkTheme = mApplication.isDark.value) {
            InflateReceipeDetailView()
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun InflateReceipeDetailView() {
      arguments?.getInt(RECEIPE_ID)?.let {
          viewModel.setReceipeId(it)
          viewModel.getReceipeById()
          Column(
              Modifier
                  .fillMaxHeight()
                  .fillMaxWidth()) {
              CircularProgressionBar(isDisplayed = true)
          }

          Column(
              Modifier
                  .fillMaxHeight()
                  .fillMaxWidth()) {
              CircularProgressionBar(isDisplayed = false)
              viewModel.recipes.value?.let { it1 -> ReceipeDetailScreen(receipeList = it1) }
          }
            if(viewModel.showSnackBar.value){
                DispalyComposable()
            }

      }
    }

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi
@Composable
fun DispalyComposable(){
    val scaffoldState = rememberScaffoldState()
    snackbarController.getScope().launch {
        snackbarController.showSnackbar(
            scaffoldState = scaffoldState,
            message = "An error occurred with this recipe",
            actionLabel = "Ok"
        )
    }
}


}