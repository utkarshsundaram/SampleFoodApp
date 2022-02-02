package com.example.samplefoodapp.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by Utkarsh Sundaram on 02-02-2022.
 */

@ExperimentalMaterialApi
@Composable
fun DefaultSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit?
) {
    val hostState = remember { SnackbarHostState() }
    Row() {


        SnackbarHost(hostState = hostState, snackbar = { snackbarData: SnackbarData ->
            Card(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = "")
                    Text(text = snackbarData.message)
                    Button(onClick = { onDismiss }) {
                        Text(text = "Hide")
                    }
                }
            }

        }, modifier = modifier)
    }
}
@Composable
fun showText(message:String){
    Text(
        text = message,
        style = MaterialTheme.typography.body2,
        color = Color.White
    )
}