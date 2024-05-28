package org.abapps.app

import android.app.Application
import org.abapps.app.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RetailApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RetailApp)
            androidLogger(
                level = Level.INFO
            )
            modules(AppModule)
            createEagerInstances()
        }
    }
}