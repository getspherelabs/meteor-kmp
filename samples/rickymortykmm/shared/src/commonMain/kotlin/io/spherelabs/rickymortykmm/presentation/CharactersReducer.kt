package io.spherelabs.rickymortykmm.presentation

import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.reducer.Reducer
import io.spherelabs.meteor.reducer.effect
import io.spherelabs.meteor.reducer.expect
import io.spherelabs.meteor.reducer.unexpected

object CharactersReducer : Reducer<CharactersState, CharactersWish, CharactersEffect> {

    override fun reduce(state: CharactersState, wish: CharactersWish): Change<CharactersState, CharactersEffect> {
        return when (wish) {
            is CharactersWish.GetCharacters -> {
                expect {
                    state.copy(
                        characters = wish.characters
                    )
                }
            }
            is CharactersWish.RequestFailed -> {
                effect {
                    CharactersEffect.Failure(wish.message)
                }
            }
            else -> {
                unexpected { state }
            }
        }
    }
}
