package co.thrivemobile.urhythm

import android.app.Application
import co.thrivemobile.urhythm.injection.AppComponent
import co.thrivemobile.urhythm.injection.AppModule
import co.thrivemobile.urhythm.injection.DaggerAppComponent
import co.thrivemobile.urhythm.injection.TimeModule

class URhythmApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .timeModule(TimeModule())
            .build()
    }
}