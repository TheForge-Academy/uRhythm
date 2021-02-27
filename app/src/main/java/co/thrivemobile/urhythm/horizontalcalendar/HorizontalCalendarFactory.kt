package co.thrivemobile.urhythm.horizontalcalendar

import androidx.paging.DataSource
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

class HorizontalCalendarFactory @Inject constructor(
    private val now: () -> Instant
) : DataSource.Factory<Long, LocalDate>() {
    override fun create(): DataSource<Long, LocalDate> {
        return HorizontalCalendarSource(now)
    }
}