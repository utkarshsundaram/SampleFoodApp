package com.example.samplefoodapp.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.samplefoodapp.R
import com.example.samplefoodapp.commonutils.DEFAULT_RECIPE_IMAGE
import com.example.samplefoodapp.commonutils.loadPicture
import com.example.samplefoodapp.domain.model.ReceipeList

@Composable
fun ReceipeDetailScreen(receipeList: ReceipeList){

    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    val items = (receipeList.ingredients).map { "$it" }

    LazyColumn(
        Modifier.fillMaxSize(),
        lazyListState,
    ) {
        item {
            //The children of the Box layout will be stacked over each other(Frame layout equivvalent)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.surface)
            ) {
                val image =
                    receipeList.featuredImage?.let {
                        loadPicture(url = it, defaultImage = DEFAULT_RECIPE_IMAGE).value
                    }
                image?.asImageBitmap()?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .graphicsLayer {
                                scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                                translationY = scrolledY * 0.5f
                                previousOffset = lazyListState.firstVisibleItemScrollOffset
                            }
                            .fillMaxHeight()
                            .fillMaxWidth()
                    )
                }
            }
            receipeList.title?.let {
                Text(
                    text = it,
                    Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(20.dp),
                       style = MaterialTheme.typography.h5
                )
            }
            Text(
                text = "How to make It ?",
                Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(20.dp),
                style = MaterialTheme.typography.h5
            )

        }

        items(items) {
            Text(
                text = it,
                Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        }
    }

}