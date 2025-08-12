package ir.fatemelyasi.note.model.di.applicationModule

import ir.fatemelyasi.note.model.di.databaseModule.DatabaseModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    [
        DatabaseModule::class,

    ]
)
@ComponentScan("ir.fatemelyasi.note")
class ApplicationModule