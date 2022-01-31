package com.example.samplefoodapp.presentation.ui.receipelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.samplefoodapp.R
import com.example.samplefoodapp.commonutils.Constant
import com.example.samplefoodapp.commonutils.Constant.PAGE_SIZE
import com.example.samplefoodapp.commonutils.Constant.RECEIPE_ID
import com.example.samplefoodapp.commonutils.getAllFoodCategories
import com.example.samplefoodapp.presentation.theme.SampleFoodAppTheme
import com.example.samplefoodapp.presentation.ui.application.BaseApplication
import com.example.samplefoodapp.presentation.ui.component.CircularProgressionBar
import com.example.samplefoodapp.presentation.ui.component.GenerateSearchAppBar
import com.example.samplefoodapp.presentation.ui.component.ReceipeCard
import com.example.samplefoodapp.presentation.ui.receipe.ReceipeDetailFragment
import com.example.samplefoodapp.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ReceipeListFragment:Fragment() {

    private val viewModel: ReceipeListViewModel by activityViewModels<ReceipeListViewModel>()

    @Inject
    lateinit var mApplication: BaseApplication

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


    @ExperimentalComposeUiApi
    @Composable
    fun InflateReceipeListView() {
        val receipeList = viewModel.recipes?.value
        val query = viewModel.queries.value
        val selectedCategory = viewModel.selectedCategory.value
        val page = viewModel.pageNo


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
            //MyDrawer()
        }) {


            Column() {
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
                    }
                }

            }
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    fun GetSearchBar() {
        val keyboardController = LocalSoftwareKeyboardController.current


        TextField(value = viewModel.queries.value,
            onValueChange = {
                viewModel.onQueriesChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(8.dp),
            label = {
                Text(text = "Search")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            leadingIcon = {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.search_icon),
                    contentDescription = "SearchIcon"
                )
            },
            keyboardActions = KeyboardActions(onDone = {
                viewModel.newSearch()
                keyboardController?.hide()
            }),
            textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = MaterialTheme.colors.surface,
            )

        )


    }

    @ExperimentalComposeUiApi
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        AppTheme(darkTheme = mApplication.isDark.value) {
            InflateReceipeListView()
        }
    }

    @Composable
    fun MyBottomBar() {
        BottomNavigation(
            elevation = 12.dp
        ) {
            BottomNavigationItem(
                icon = { Icons.Default.Favorite },
                selected = false,
                onClick = {}
            )
            BottomNavigationItem(
                icon = { Icons.Default.MoreVert },
                selected = true,
                onClick = {}
            )
            BottomNavigationItem(
                icon = { Icons.Default.Add },
                selected = false,
                onClick = {}
            )
        }
    }


    @Composable
    fun MyDrawer() {
        Column() {
            Text(text = "Item1")
            Text(text = "Item2")
            Text(text = "Item3")
            Text(text = "Item4")
            Text(text = "Item5")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(Constant.TAG, "Fragment back pressed invoked")
                    findNavController().navigate(R.id.action_receipeDetailFragment_to_receipeListFragment)
                }
            })
    }
}