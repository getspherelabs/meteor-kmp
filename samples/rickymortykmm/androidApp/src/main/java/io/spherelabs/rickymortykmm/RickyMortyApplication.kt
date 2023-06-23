package io.spherelabs.rickymortykmm

import android.app.Application
import io.spherelabs.rickymortykmm.android.di.viewModelModule
import io.spherelabs.rickymortykmm.di.initKoin

class RickyMortyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            modules(viewModelModule)
        }
    }
}
