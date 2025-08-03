package ir.fatemelyasi.note.di.applicationModule

import ir.fatemelyasi.note.di.databaseModule.DatabaseModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    [
        DatabaseModule::class,

    ]
)
@ComponentScan("ir.fatemelyasi.note")
class ApplicationModule