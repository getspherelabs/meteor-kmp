package io.spherelabs.countercompose.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CountScreen(
    modifier: Modifier = Modifier,
    viewModel: CountViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState = viewModel.store.state.collectAsState()

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LaunchedEffect(true) {
        viewModel.store.effect.collectLatest { effect ->
            when (effect) {
                is CountEffect.Failure -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = modifier,
                text = uiState.value.count.toString(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )

            Spacer(modifier = modifier.height(16.dp))

            Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = {
                        viewModel.wish(CountWish.Increase)
                    }
                ) {
                    Text(text = "Add")
                }
                Spacer(modifier = modifier.width(8.dp))

                Button(
                    onClick = {
                        viewModel.wish(CountWish.Decrease)
                    }
                ) {
                    Text(text = "Decrease")
                }

                Spacer(
                    modifier = modifier.width(8.dp)
                )

                Button(
                    onClick = {
                        viewModel.wish(CountWish.Reset)
                    }
                ) {
                    Text(text = "Reset")
                }
            }
        }
    }
}

@Preview
@Composable
fun CountPreview() {
    Surface {
    }
}
