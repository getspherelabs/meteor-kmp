package io.spherelabs.rickymortykmm.local.entity

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val origin: String,
    val location: String,
    val image: String
)
