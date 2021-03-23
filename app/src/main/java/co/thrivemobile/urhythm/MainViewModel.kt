package co.thrivemobile.urhythm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import co.thrivemobile.urhythm.horizontalcalendar.Day
import co.thrivemobile.urhythm.horizontalcalendar.HorizontalCalendarFactory
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
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
        private const val CHART_LABEL = "DAY_CHART"
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

    private val dayData = mutableListOf<Entry>()
    private val _lineDataSet = MutableLiveData(LineDataSet(dayData, CHART_LABEL))
    val lineDataSet: LiveData<LineDataSet> = _lineDataSet

    init {
        dayData.add(Entry(0f, 5f))
        dayData.add(Entry(1f, 4f))
        dayData.add(Entry(2f, 7f))
        dayData.add(Entry(3f, 8f))
        dayData.add(Entry(4f, 10f))
        dayData.add(Entry(5f, 7f))
        dayData.add(Entry(6f, 3f))
        dayData.add(Entry(7f, 6f))
        dayData.add(Entry(8f, 5f))
        dayData.add(Entry(9f, 8f))

        _lineDataSet.value = LineDataSet(dayData, CHART_LABEL)
    }

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