package io.spherelabs.rickymortykmm.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.spherelabs.meteor.middleware.Middleware
import io.spherelabs.rickymortykmm.domain.DefaultGetCharacterByIdUseCase
import io.spherelabs.rickymortykmm.domain.DefaultGetCharactersUseCase
import io.spherelabs.rickymortykmm.domain.GetCharacterByIdUseCase
import io.spherelabs.rickymortykmm.domain.GetCharactersUseCase
import io.spherelabs.rickymortykmm.presentation.CharactersMiddleware
import io.spherelabs.rickymortykmm.presentation.CharactersWish
import io.spherelabs.rickymortykmm.remote.DefaultRickyMortyService
import io.spherelabs.rickymortykmm.remote.RickyMortyService
import io.spherelabs.rickymortykmm.repository.DefaultRickyMortyRepository
import io.spherelabs.rickymortykmm.repository.RickyMortyRepository
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModule = module {
    single<RickyMortyService> { DefaultRickyMortyService(get()) }

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }
}

val repositoryModule = module {
    single<RickyMortyRepository> { DefaultRickyMortyRepository(get()) }
}

val useCaseModule = module {
    single<GetCharactersUseCase> { DefaultGetCharactersUseCase(get()) }
    single<GetCharacterByIdUseCase> { DefaultGetCharacterByIdUseCase(get()) }
}

val middlewareModule = module {
    single<Middleware<CharactersWish>>(named("characters")) {
        CharactersMiddleware(get())
    }
}
