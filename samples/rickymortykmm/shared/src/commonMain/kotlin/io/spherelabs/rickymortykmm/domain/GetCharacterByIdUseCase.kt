package io.spherelabs.rickymortykmm.domain

import io.spherelabs.rickymortykmm.remote.dto.CharacterDto
import io.spherelabs.rickymortykmm.repository.RickyMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetCharacterByIdUseCase {
    fun execute(id: Int): Flow<Result<CharacterDto>>
}

class DefaultGetCharacterByIdUseCase(
    private val repository: RickyMortyRepository
) : GetCharacterByIdUseCase {
    override fun execute(id: Int): Flow<Result<CharacterDto>> {
        return flow {
            try {
                Result.success(repository.fetchCharacterById(id))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
