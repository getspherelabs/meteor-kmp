package io.spherelabs.rickymortykmm.domain

import io.spherelabs.rickymortykmm.remote.dto.CharactersResponse
import io.spherelabs.rickymortykmm.repository.RickyMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetCharactersUseCase {
    fun execute(): Flow<Result<CharactersResponse>>
}

class DefaultGetCharactersUseCase(
    private val repository: RickyMortyRepository
) : GetCharactersUseCase {

    override fun execute(): Flow<Result<CharactersResponse>> {
        return flow {
            try {
                emit(Result.success(repository.fetchCharacters()))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }
}
