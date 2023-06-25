package io.spherelabs.rickymortykmm.presentation.detail

import io.spherelabs.meteor.middleware.Middleware
import io.spherelabs.rickymortykmm.domain.GetCharacterByIdUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object CharacterDetailMiddleware : Middleware<CharacterDetailState, CharacterDetailWish>, KoinComponent {

    private val getCharacterByIdUseCase: GetCharacterByIdUseCase by inject()

    override suspend fun process(state: CharacterDetailState, wish: CharacterDetailWish, next: suspend (CharacterDetailWish) -> Unit) {
        when (wish) {
            is CharacterDetailWish.GetCharacterById -> {
                handleGettingCharacterById(id = wish.id, next)
            }

            else -> {}
        }
    }

    private suspend fun handleGettingCharacterById(id: Int, next: suspend (CharacterDetailWish) -> Unit) {
        getCharacterByIdUseCase.execute(id).onStart {
            next.invoke(CharacterDetailWish.GettingLoading)
        }.collectLatest { result ->
            result.onSuccess { newCharacter ->
                next.invoke(CharacterDetailWish.GettingCompleted(character = newCharacter))
            }.onFailure {
                next.invoke(CharacterDetailWish.Toast(msg = it.message ?: "Error is occured."))
            }
        }
    }
}
