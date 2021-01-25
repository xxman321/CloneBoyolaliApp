package com.ydh.budayabyl

import android.app.Application
import com.ydh.budayabyl.di.firebaseModule
import com.ydh.budayabyl.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                firebaseModule,
                viewModelModule

                )
        }
    }
}