package co.thrivemobile.urhythm.horizontalcalendar

import androidx.paging.PageKeyedDataSource
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class HorizontalCalendarSource(
    private val now: () -> Instant
) : PageKeyedDataSource<Long, Day>() {

    private val today: LocalDate
        get() {
            return now().atZone(ZoneId.systemDefault()).toLocalDate()
        }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Day>
    ) {
        val day = Day(true, today)
        callback.onResult(mutableListOf(day), -1, 1)
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, Day>
    ) {
        val previousDay = today.plusDays(params.key)
        val day = Day(
            isSelected = false,
            date = previousDay
        )
        callback.onResult(mutableListOf(day), params.key - 1)
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, Day>
    ) {
        val nextDay = today.plusDays(params.key)
        val day = Day(
            isSelected = false,
            date = nextDay
        )
        callback.onResult(mutableListOf(day), params.key + 1)
    }
}