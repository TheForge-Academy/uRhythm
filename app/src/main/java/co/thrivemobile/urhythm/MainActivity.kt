package co.thrivemobile.urhythm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import co.thrivemobile.urhythm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.currentDate.observe(this) { currentDay ->
            binding.toolbar.title = currentDay.dayOfWeek
            binding.toolbar.subtitle = currentDay.date
        }
    }
}