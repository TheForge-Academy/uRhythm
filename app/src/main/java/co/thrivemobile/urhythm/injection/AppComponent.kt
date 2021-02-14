package co.thrivemobile.urhythm.injection

import co.thrivemobile.urhythm.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        TimeModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
}