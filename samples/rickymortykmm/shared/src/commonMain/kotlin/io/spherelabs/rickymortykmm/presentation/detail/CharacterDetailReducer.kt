package io.spherelabs.rickymortykmm.presentation.detail

import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.reducer.Reducer
import io.spherelabs.meteor.reducer.expect
import io.spherelabs.meteor.reducer.unexpected

object CharacterDetailReducer : Reducer<CharacterDetailState, CharacterDetailWish, CharacterDetailEffect> {

    override fun reduce(state: CharacterDetailState, wish: CharacterDetailWish): Change<CharacterDetailState, CharacterDetailEffect> {
        return when (wish) {
            is CharacterDetailWish.GettingCompleted -> {
                expect {
                    state.copy(
                        isLoading = false,
                        character = wish.character
                    )
                }
            }

            is CharacterDetailWish.Toast -> {
                val newState = state.copy(isLoading = false)
                val newEffect = CharacterDetailEffect.Failure(wish.msg)

                Change(effect = newEffect, state = newState)
            }

            is CharacterDetailWish.GettingLoading -> {
                expect {
                    state.copy(isLoading = true)
                }
            }
            else -> {
                unexpected { state }
            }
        }
    }
}
