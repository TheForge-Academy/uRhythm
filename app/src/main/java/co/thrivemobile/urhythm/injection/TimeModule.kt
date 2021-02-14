package co.thrivemobile.urhythm.injection

import dagger.Module
import dagger.Provides
import java.time.Instant

@Module
class TimeModule {
    @Provides
    fun provideNow(): () -> Instant {
        return { Instant.now() }
    }
}