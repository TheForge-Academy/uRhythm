package co.thrivemobile.urhythm

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.thrivemobile.urhythm.databinding.ActivityMainBinding
import co.thrivemobile.urhythm.horizontalcalendar.CenterSmoothScroller
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
        val centerSmoothScroller = CenterSmoothScroller(this)

        binding.horizontalCalendar.apply {
            adapter = horizontalCalendarAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.toolbar.setOnMenuItemClickListener {
            handleMenuItemClick(it)
        }

        mainViewModel.currentDate.observe(this) { currentDay ->
            binding.toolbar.title = currentDay.dayOfWeek
            binding.toolbar.subtitle = currentDay.date
        }

        mainViewModel.horizontalCalendarSource.observe(this) { dates ->
            horizontalCalendarAdapter.submitList(dates)
        }

        mainViewModel.goToPosition.observe(this) { position ->
            if (position == -1) {
                return@observe
            }

            centerSmoothScroller.targetPosition = position
            horizontalCalendarAdapter.onSelect(position)
            binding.horizontalCalendar
                .layoutManager
                ?.startSmoothScroll(centerSmoothScroller)
        }
    }

    private fun handleMenuItemClick(item: MenuItem) = when (item.itemId) {
        R.id.today -> {
            mainViewModel.resetCalendarPosition()
            true
        }
        else -> false
    }
}