package io.spherelabs.rickymortykmm.remote.dto

@kotlinx.serialization.Serializable
data class CharactersResponse(
    val results: List<CharacterDto>
)

@kotlinx.serialization.Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: LocationDto,
    val location: LocationDto,
    val image: String
)

@kotlinx.serialization.Serializable
data class LocationDto(
    val name: String
)
