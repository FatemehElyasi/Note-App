package ir.fatemelyasi.note.view

import android.app.Application
import ir.fatemelyasi.note.model.di.applicationModule.ApplicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(ApplicationModule().module)
        }
    }
}