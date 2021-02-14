package co.thrivemobile.urhythm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class MainViewModel : ViewModel() {

    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("LLL d, yyyy")
    }

    val currentDate: LiveData<DayInfo> = MutableLiveData<DayInfo>().apply {
        val now = LocalDate.now()
        value = DayInfo(
            dayOfWeek = now.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
            date = dateFormatter.format(now)
        )
    }
}