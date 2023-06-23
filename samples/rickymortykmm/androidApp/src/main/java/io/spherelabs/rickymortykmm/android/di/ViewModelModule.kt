package io.spherelabs.rickymortykmm.android.di

import io.spherelabs.rickymortykmm.presentation.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::CharactersViewModel)
}
