package io.spherelabs.rickymortykmm.android.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import io.spherelabs.rickymortykmm.presentation.detail.CharacterDetailEffect
import io.spherelabs.rickymortykmm.presentation.detail.CharacterDetailState
import io.spherelabs.rickymortykmm.presentation.detail.CharacterDetailViewModel
import io.spherelabs.rickymortykmm.presentation.detail.CharacterDetailWish
import io.spherelabs.rickymortykmm.remote.dto.CharacterDto
import io.spherelabs.rickymortykmm.remote.dto.LocationDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CharacterDetailRoute(
    id: Int,
    viewModel: CharacterDetailViewModel
) {
    CharacterDetailScreen(
        id = id,
        wish = { newWish ->
            viewModel.wish(newWish)
        },
        detailState = viewModel.state,
        detailEffect = viewModel.effect
    )
}

@Composable
fun CharacterDetailScreen(
    id: Int,
    wish: (CharacterDetailWish) -> Unit,
    detailState: StateFlow<CharacterDetailState>,
    detailEffect: Flow<CharacterDetailEffect>
) {
    val uiState = detailState.collectAsState()

    LaunchedEffect(key1 = true) {
        wish.invoke(CharacterDetailWish.GetCharacterById(id))

        detailEffect.collectLatest {
        }
    }
    if (uiState.value.isLoading) {
        LoadingIndicator()
    } else {
        CharacterDetailContext(character = uiState.value.character)
    }
}

@Composable
fun CharacterDetailContext(
    modifier: Modifier = Modifier,
    character: CharacterDto?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = character?.name ?: "Unknown",
            style = MaterialTheme.typography.h4
        )

        Spacer(modifier = modifier.height(8.dp))

        Image(
            modifier = modifier.size(200.dp),
            painter = rememberImagePainter(data = character?.image ?: "Unknown"),
            contentDescription = null
        )

        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = character?.gender ?: "Unknown",
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailPreview() {
    CharacterDetailContext(
        character = CharacterDto(
            name = "Ricky",
            id = 1,
            status = "",
            species = "",
            gender = "Male",
            origin = LocationDto(name = ""),
            image = "",
            location = LocationDto(name = "")
        )
    )
}
