package io.spherelabs.rickymortykmm.repository

import io.spherelabs.rickymortykmm.remote.RickyMortyService
import io.spherelabs.rickymortykmm.remote.dto.CharacterDto
import io.spherelabs.rickymortykmm.remote.dto.CharactersResponse

interface RickyMortyRepository {
    suspend fun fetchCharacters(): CharactersResponse
    suspend fun fetchCharacterById(id: Int): CharacterDto
}

class DefaultRickyMortyRepository(
    private val service: RickyMortyService
) : RickyMortyRepository {
    override suspend fun fetchCharacters(): CharactersResponse {
        return service.fetchCharacters()
    }

    override suspend fun fetchCharacterById(id: Int): CharacterDto {
        return service.fetchCharacterById(id)
    }
}
