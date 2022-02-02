package com.example.samplefoodapp.presentation.ui.receipelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.samplefoodapp.R
import com.example.samplefoodapp.commonutils.Constant
import com.example.samplefoodapp.commonutils.Constant.PAGE_SIZE
import com.example.samplefoodapp.commonutils.Constant.RECEIPE_ID
import com.example.samplefoodapp.commonutils.getAllFoodCategories
import com.example.samplefoodapp.presentation.ui.application.BaseApplication
import com.example.samplefoodapp.presentation.ui.component.*
import com.example.samplefoodapp.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ReceipeListFragment:Fragment() {

    private val viewModel: ReceipeListViewModel by activityViewModels<ReceipeListViewModel>()
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
    ): View {
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
    @Composable
    fun InflateReceipeListView() {
        val receipeList = viewModel.recipes?.value
        val query = viewModel.queries.value
        val selectedCategory = viewModel.selectedCategory.value
        val page = viewModel.pageNo
        val scaffoldState = rememberScaffoldState()

        //A generalised way to define all the layout type ie body topbar bootombar drawer layout in onego
        Scaffold(topBar = {
            GenerateSearchAppBar(
                queries = query,
                onQueryChanged = viewModel::onQueryChanged,
                onExecuteSearch = { viewModel.newSearch() },
                newSearch = { viewModel.newSearch() },
                categories = getAllFoodCategories(),
                selectedCategory = selectedCategory,
                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                categoriesScrollPosition = viewModel.categoriesScrollPosition,
                onChangedCategoryScrollPosition = viewModel::onChangedCategoryScrollPosition,
                switchDarkMode = { mApplication.toggleDarkTheme() },
                switchLightMode = { mApplication.toggleLightTheme() }
            )
        }, bottomBar = {// MyBottomBar() }, drawerContent = {
            //MyDrawer()xxxx

        }, scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            }) {
            Column {
                //Similar to Frame Layout
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.surface)
                )

                {
                    receipeList?.let {
                        LazyColumn {
                            itemsIndexed(
                                items = it
                            ) { index, recipe ->
                                viewModel.onChangedCategoryScrollPosition(index.toFloat())

                                if((index + 1) >= (page * PAGE_SIZE) && !viewModel.loadingProgressBar.value){
                                    viewModel.nextPage()
                                }
                                ReceipeCard(receipe = recipe, onClick = {
                                    val result = Bundle()
                                    recipe?.id?.let { it1 -> result.putInt(RECEIPE_ID, it1) }
                                    findNavController().
                                    navigate(R.id.action_receipeListFragment_to_receipeDetailFragment,result)
                                })
                            }
                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight()
                        .background(color = MaterialTheme.colors.surface),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                        CircularProgressionBar(viewModel.loadingProgressBar.value)
                        if(viewModel.showSnackBar.value){
                            DefaultSnackbar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

            }
        }
    }


    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AppTheme(darkTheme = mApplication.isDark.value) {
            InflateReceipeListView()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(Constant.TAG, "Fragment back pressed invoked")
                    findNavController().navigate(R.id.action_receipeDetailFragment_to_receipeListFragment)
                }
            })
    }



}