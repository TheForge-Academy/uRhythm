package co.thrivemobile.urhythm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.thrivemobile.urhythm.databinding.ActivityMainBinding
import co.thrivemobile.urhythm.horizontalcalendar.HorizontalCalendarAdapter
import co.thrivemobile.urhythm.injection.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val mainViewModel: MainViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as URhythmApplication).appComponent.inject(this)

        val horizontalCalendarAdapter = HorizontalCalendarAdapter()

        binding.horizontalCalendar.apply {
            adapter = horizontalCalendarAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        mainViewModel.currentDate.observe(this) { currentDay ->
            binding.toolbar.title = currentDay.dayOfWeek
            binding.toolbar.subtitle = currentDay.date
        }

        mainViewModel.horizontalCalendarSource.observe(this) { dates ->
            horizontalCalendarAdapter.submitList(dates)
        }
    }
}