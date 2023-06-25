package io.spherelabs.rickymortykmm.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import io.spherelabs.rickymortykmm.presentation.CharactersEffect
import io.spherelabs.rickymortykmm.presentation.CharactersState
import io.spherelabs.rickymortykmm.presentation.CharactersViewModel
import io.spherelabs.rickymortykmm.presentation.CharactersWish
import io.spherelabs.rickymortykmm.remote.dto.CharacterDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CharactersRoute(
    viewModel: CharactersViewModel,
    navigateToDetail: (Int) -> Unit
) {
    CharacterScreen(
        wish = {
            viewModel.wish(it)
        },
        state = viewModel.state,
        effect = viewModel.effect,
        routeToDetail = {
            navigateToDetail.invoke(it)
        }
    )
}

@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier,
    wish: (CharactersWish) -> Unit,
    state: StateFlow<CharactersState>,
    effect: Flow<CharactersEffect>,
    routeToDetail: (Int) -> Unit
) {
    val uiState = state.collectAsState()

    LaunchedEffect(key1 = true) {
        wish(CharactersWish.CharacterStarted)
        effect.collectLatest { effectResult ->
            when (effectResult) {
                is CharactersEffect.Failure -> {}
                is CharactersEffect.GetDetail -> {
                    routeToDetail(effectResult.id)
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Characters(
            characters = uiState.value.characters,
            onDetailClick = { newId ->
                wish.invoke(CharactersWish.GetDetail(newId))
            }
        )
    }
}

@Composable
fun Characters(
    modifier: Modifier = Modifier,
    characters: List<CharacterDto>,
    onDetailClick: (Int) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(characters) { character ->
            CharacterItem(character = character, onClick = { onDetailClick(character.id) })
        }
    }
}
