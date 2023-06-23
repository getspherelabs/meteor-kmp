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
import io.spherelabs.rickymortykmm.presentation.CharactersWish
import io.spherelabs.rickymortykmm.remote.dto.CharacterDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier,
    wish: (CharactersWish) -> Unit,
    state: StateFlow<CharactersState>,
    effect: Flow<CharactersEffect>
) {
    val uiState = state.collectAsState()

    LaunchedEffect(key1 = true) {
        wish(CharactersWish.CharacterStarted)
        effect.collectLatest {
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Characters(characters = uiState.value.characters)
    }
}

@Composable
fun Characters(
    modifier: Modifier = Modifier,
    characters: List<CharacterDto>
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(characters) { character ->
            CharacterItem(character = character)
        }
    }
}
