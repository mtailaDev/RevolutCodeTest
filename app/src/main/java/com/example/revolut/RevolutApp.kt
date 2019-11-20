package com.example.revolut

import android.app.Application
import com.example.revolut.core.coreModule
import com.example.revolut.currencyrate.di.currencyRateModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RevolutApp : Application(){

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@RevolutApp)
            modules(
                listOf(
                    coreModule,
                    currencyRateModule
                )
            )
        }
    }

    companion object {
        lateinit var instance: RevolutApp
            private set
    }

}