package di

// class ServiceModuleTest : KoinTest {
//
//    val service: RickyMortyService by inject()
//
//    @Test
//    fun `should inject api service`() {
//        startKoin {
//            modules(module {
//                single {
//                    HttpClient {
//                        install(ContentNegotiation) {
//                            json(
//                                Json {
//                                    ignoreUnknownKeys = true
//                                    prettyPrint = true
//                                    isLenient = true
//                                }
//                            )
//                        }
//
//                        install(Logging) {
//                            logger = Logger.DEFAULT
//                            level = LogLevel.ALL
//                        }
//                    }
//                }
//                single<RickyMortyService> { DefaultRickyMortyService(get()) }
//            })
//        }
//
//        assertNotNull(service)
//    }
// }
