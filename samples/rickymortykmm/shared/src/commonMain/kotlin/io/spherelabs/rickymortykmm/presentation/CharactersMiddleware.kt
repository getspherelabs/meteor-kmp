package io.spherelabs.rickymortykmm.presentation

import io.spherelabs.meteor.middleware.Middleware
import io.spherelabs.rickymortykmm.domain.GetCharactersUseCase
import kotlinx.coroutines.flow.collectLatest

class CharactersMiddleware(
    private val getCharactersUseCase: GetCharactersUseCase
) : Middleware<CharactersState, CharactersWish> {

    override suspend fun process(state: CharactersState, wish: CharactersWish, next: suspend (CharactersWish) -> Unit) {
        when (wish) {
            CharactersWish.CharacterStarted -> {
                getCharactersUseCase.execute().collectLatest { result ->
                    result.onSuccess {
                        next.invoke(CharactersWish.GetCharacters(it.results))
                    }.onFailure {
                        val failureMsg = it.message ?: "Error is occurred."
                        next.invoke(CharactersWish.RequestFailed(failureMsg))
                    }
                }
            }
            else -> {}
        }
    }
}
