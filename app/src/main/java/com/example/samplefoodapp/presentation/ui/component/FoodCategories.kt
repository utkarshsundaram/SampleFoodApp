package com.example.samplefoodapp.presentation.ui.component

import android.graphics.Color

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.Color.Companion.White

import androidx.compose.ui.unit.dp
@Composable
fun FoodCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
){
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        elevation = 8.dp,
        color = if(isSelected) LightGray else MaterialTheme.colors.primary
        ,shape = MaterialTheme.shapes.medium


    ) {
        Row(modifier = Modifier.
        toggleable(value = isSelected,
            onValueChange = {
                onSelectedCategoryChanged(category)
                onExecuteSearch()
            })

            )
         {
            Text(
                modifier=Modifier.padding(10.dp),
                text = category,
                style = MaterialTheme.typography.body2,
                color = White
            )
        }
    }
}
