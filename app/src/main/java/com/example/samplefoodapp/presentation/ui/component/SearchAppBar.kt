package com.example.samplefoodapp.presentation.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.samplefoodapp.R
import com.example.samplefoodapp.commonutils.FoodCategories
import com.example.samplefoodapp.commonutils.getAllFoodCategories
import com.example.samplefoodapp.commonutils.getFoodcategoriesIndex
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun GenerateSearchAppBar(queries: String,onQueryChanged: (String) -> Unit,
                         onExecuteSearch: () -> Unit,
                         newSearch:()->Unit,
                         categories: ArrayList<FoodCategories>,
                         selectedCategory: FoodCategories?,
                         onSelectedCategoryChanged: (String) -> Unit,
                         categoriesScrollPosition: Float,
                         onChangedCategoryScrollPosition:(Float)->Unit,
                         switchDarkMode:()->Unit,
                         switchLightMode:()->Unit) {
    val bitmap: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.search_icon)
    val keyboardController = LocalSoftwareKeyboardController.current

    val rowScrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp,
    ) {
        Column() {
            Row(modifier = Modifier.fillMaxWidth()) {

                TextField(
                    value = queries,
                    onValueChange = {
                        onQueryChanged(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(8.dp),
                    shape = RoundedCornerShape(5.dp),
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    leadingIcon = {
                        Icon(
                            bitmap = bitmap,
                            contentDescription = "SearchIcon"
                        )
                    },
                    keyboardActions = KeyboardActions(onDone = {
                        newSearch()
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
//                TopAppBar(
//                    backgroundColor = Color.White,
//                    elevation = 0.dp,
//                     title = {
//                        Text(text = "")
//                    },
//                    actions = {
//                        var menuExpanded by remember { mutableStateOf(false) }
//
//                        IconButton(onClick = { menuExpanded = true }) {
//                            Icon(Icons.Default.MoreVert, contentDescription = null)
//                        }
//
//                        DropdownMenu(
//
//                            expanded = menuExpanded,
//                            onDismissRequest = {
//                                menuExpanded = false
//                            },
//                        ) {
//                            DropdownMenuItem(onClick = {switchDarkMode()}) {
//                                Text("Dark Mode")
//                            }
//                            Divider()
//                            DropdownMenuItem(onClick = {switchLightMode()}) {
//                                Text("Light Mode")
//                            }
//                        }
//                    },
//                )

                //Menu bar for dark and light theme
                Box(Modifier.
                wrapContentWidth().
                wrapContentHeight().padding(5.dp),
                    Alignment.Center) {

                    Box() {
                        var expanded by remember { mutableStateOf(false) }

                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                        }

                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(onClick = { switchDarkMode() }) {
                                Text("Dark Mode",Modifier.weight(1f))
                            }
                            Divider()
                            DropdownMenuItem(onClick = { switchLightMode() }) {
                                Text("Light Mode",Modifier.weight(1f))
                            }
                        }

                    }
                }
            }
            LazyRow(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(), state = rowScrollState
            ) {
                coroutineScope.launch {
                    rowScrollState.animateScrollToItem(categoriesScrollPosition.toInt())
                }
                itemsIndexed(
                    items = getAllFoodCategories()
                ) { index, categories ->
                    FoodCategoryChip(
                        category = categories.value,
                        isSelected = selectedCategory == categories,
                        onSelectedCategoryChanged = ({
                            onSelectedCategoryChanged(it)
                            onChangedCategoryScrollPosition(getFoodcategoriesIndex(it).toFloat())
                        }),
                        onExecuteSearch = ({
                            onExecuteSearch()
                        })

                    )
                }
            }
        }
    }


    //        OutlinedTextField(
//
//            value = viewModel.queries.value,
//            onValueChange = { viewModel.onQueriesChanged(it) },
//            label = { Text("Test") },
//            keyboardOptions =
//            KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
//            leadingIcon = {
//                Icon(
//                    bitmap = ImageBitmap.imageResource(id = R.drawable.search_icon),
//                    contentDescription = "SearchIcon"
//                )
//            },
//            keyboardActions =  KeyboardActions(onDone = {
//                viewModel.newSearch(viewModel.queries.value)
//                keyboardController?.hide()
//            }),
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = MaterialTheme.colors.secondary,
//                unfocusedBorderColor = MaterialTheme.colors.secondary,
//                focusedLabelColor = MaterialTheme.colors.secondary,
//                cursorColor = MaterialTheme.colors.primaryVariant
//            ),
//            singleLine=true,
//            textStyle = TextStyle(color = MaterialTheme.colors.onSurface )
//        )


    //        Column() {
//          Surface(
//              modifier = Modifier.fillMaxWidth(),
//              color = MaterialTheme.colors.primaryVariant
//              , elevation = 8.dp
//          ) {
//                Row(modifier =
//                Modifier.fillMaxWidth(0.90f)) {
//                    TextField(
//                        modifier = Modifier
//                            .fillMaxWidth(.9f)
//                            .padding(8.dp)
//                        ,
//                        value = viewModel.queries,
//                        onValueChange = { viewModel.onQueriesChanged(it) },
//                        label = {
//                            Text(text = "Search")
//                        },
//                        keyboardOptions = KeyboardOptions(
//                            keyboardType = KeyboardType.Text,
//                            imeAction = ImeAction.Done,
//                        ),
//                        leadingIcon = {
//                            Icon(Icons.Filled.Search)
//                        },
//                        onImeActionPerformed = { action, softKeyboardController ->
//                            if (action == ImeAction.Done) {
//                                viewModel.newSearch(viewModel.queries.value)
//                                softKeyboardController?.hideSoftwareKeyboard()
//                            }
//                        },
//                        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
//                        backgroundColor = MaterialTheme.colors.surface
//                    )
//                }
//          }
//
//            receipeList?.let {
//                LazyColumn {
//                    itemsIndexed(
//                        items = it
//                    ) { index, recipe ->
//                        ReceipeCard(receipe = recipe, onClick = {})
//                    }
//                }
//            }
//        }
////        Column(
////            Modifier
////                .fillMaxSize()
////                .padding(10.dp, 10.dp, 10.dp, 10.dp)
////                .verticalScroll(rememberScrollState())
////        ) {
////
////            Image(painter = painterResource(id = R.drawable.happy_menu),
////                contentDescription = "happy meal",
////                Modifier
////                    .padding(10.dp)
////                    .height(100.dp)
////                    .width(100.dp)
////                    .align(Alignment.CenterHorizontally), contentScale = ContentScale.Fit,
////            )
////            Divider(Modifier.height(1.dp), Color.LightGray)
////            Spacer(modifier = Modifier.padding(10.dp))
////            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
////                Text(text = "Happy Meal", Modifier.padding(10.dp)
////                    ,
////                    style = TextStyle(color = Color.Black,
////                        fontSize = 20.sp), )
////                Spacer(modifier = Modifier.padding(10.dp))
////                Text(text = "Rs 100",
////                    Modifier
////                        .padding(10.dp)
////                        .align(Alignment.CenterVertically)
////                    ,style = TextStyle(color = Color.Black,
////                        fontSize = 16.sp)
////                )
////            }
////            val context= LocalContext.current
////            Spacer(modifier = Modifier.padding(10.dp))
////            Text(text = "Calorie Value 500",
////                Modifier.padding(10.dp),style = TextStyle(color = Color.Black,
////                fontSize = 14.sp)
////            )
////            Spacer(modifier = Modifier.padding(10.dp))
////            Button(onClick = { Toast.makeText(context, "This is my Toast message!",
////                Toast.LENGTH_LONG).show()}, modifier = Modifier
////                .width(200.dp)
////                .align(Alignment.CenterHorizontally)
////                .clip(RoundedCornerShape(4.dp))) {
////                Text(text = "Order Now")
////            }
////
////        }

}




