package io.spherelabs.rickymortykmm.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.spherelabs.rickymortykmm.remote.dto.CharacterDto
import io.spherelabs.rickymortykmm.remote.dto.CharactersResponse
import org.koin.core.component.KoinComponent

interface RickyMortyService {
    suspend fun fetchCharacters(): CharactersResponse
    suspend fun fetchCharacterById(id: Int): CharacterDto
}

class DefaultRickyMortyService(
    private val httpClient: HttpClient,
    val baseUrl: String = "https://rickandmortyapi.com"
) : RickyMortyService, KoinComponent {
    override suspend fun fetchCharacters(): CharactersResponse {
        return httpClient.get("$baseUrl/api/character").body<CharactersResponse>()
    }

    override suspend fun fetchCharacterById(id: Int): CharacterDto {
        return httpClient.get("$baseUrl/api/character/$id").body<CharacterDto>()
    }
}
