package io.spherelabs.androidapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CounterScreen(
    modifier: Modifier = Modifier,
    text: String,
    addClick: () -> Unit,
    resetClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = modifier,
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )

        Spacer(modifier = modifier.height(16.dp))

        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = addClick) {
                Text(text = "Add")
            }
            Spacer(modifier = modifier.width(8.dp))
            Button(onClick = resetClick) {
                Text(text = "Reset")
            }
        }
    }
}

@Preview
@Composable
fun CounterPreview() {
    Surface {
        CounterScreen(text = "0", addClick = {}, resetClick = {})
    }
}
