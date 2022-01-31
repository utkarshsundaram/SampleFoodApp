package com.example.samplefoodapp.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.samplefoodapp.R
import com.example.samplefoodapp.commonutils.DEFAULT_RECIPE_IMAGE
import com.example.samplefoodapp.commonutils.loadPicture
import com.example.samplefoodapp.domain.model.ReceipeList
@Composable

fun ReceipeCard( receipe:ReceipeList,onClick: () -> Unit){
Card(shape = MaterialTheme.shapes.small, modifier = Modifier
    .padding(10.dp)
    .fillMaxWidth()
    .fillMaxHeight().clickable(onClick = onClick),elevation = 8.dp) {
    Column() {


        receipe.featuredImage?.let {
            val image =loadPicture(url = it, defaultImage = DEFAULT_RECIPE_IMAGE).value

            image?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "happy meal", modifier = Modifier
                        .fillMaxHeight(0.75f)
                        .fillMaxWidth()
                        .padding(5.dp)
                    , contentScale = ContentScale.FillWidth
                )
            }
        }

        receipe.title?.let { title ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top=12.dp, bottom=12.dp, start = 8.dp, end=8.dp)
            ){
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start)
                    ,
                    style = MaterialTheme.typography.h5
                )
                val rank = receipe.rating.toString()
                Text(
                    text = rank,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .align(Alignment.CenterVertically)
                    ,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}
}