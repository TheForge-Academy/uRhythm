package co.thrivemobile.urhythm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import co.thrivemobile.urhythm.horizontalcalendar.Day
import co.thrivemobile.urhythm.horizontalcalendar.HorizontalCalendarFactory
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

class MainViewModel @Inject constructor(
    horizontalCalendarFactory: HorizontalCalendarFactory,
    private val now: () -> Instant
) : ViewModel() {

    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("LLL d, yyyy")
    }

    val currentDate: LiveData<DayInfo> = MutableLiveData<DayInfo>().apply {
        val now = now().atZone(ZoneId.systemDefault()).toLocalDate()
        value = DayInfo(
            dayOfWeek = now.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
            date = dateFormatter.format(now)
        )
    }

    private val _goToPosition = MutableLiveData(-1)
    val goToPosition: LiveData<Int> = _goToPosition

    val horizontalCalendarSource: LiveData<PagedList<Day>> =
        horizontalCalendarFactory.toLiveData(30)

    fun resetCalendarPosition() {
        horizontalCalendarSource.value?.apply {
            val today = now().atZone(ZoneId.systemDefault()).toLocalDate()
            val position = binarySearch { day ->
                day.date.compareTo(today)
            }
            _goToPosition.value = position
        }
    }
}