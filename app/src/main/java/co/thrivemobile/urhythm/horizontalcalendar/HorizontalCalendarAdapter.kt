package co.thrivemobile.urhythm.horizontalcalendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.thrivemobile.urhythm.R
import co.thrivemobile.urhythm.databinding.ItemHorizontalCalendarDateBinding
import java.time.format.TextStyle
import java.util.Locale

class HorizontalCalendarAdapter
    : PagedListAdapter<Day, HorizontalCalendarAdapter.CalendarDateViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Day>() {
            override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
                return true
            }

            override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHorizontalCalendarDateBinding.inflate(layoutInflater, parent, false)
        return CalendarDateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarDateViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)

            holder.itemView.setOnClickListener {
                onSelect(position)
            }
        }
    }

    fun onSelect(position: Int) {
        currentList?.let {
            it.forEach { day -> day.isSelected = false }
            getItem(position)?.isSelected = true
            notifyDataSetChanged()
        }
    }

    class CalendarDateViewHolder(
        private val binding: ItemHorizontalCalendarDateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(day: Day) = binding.apply {
            handleSelection(day)

            dayOfWeek.text = day.date.dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.ROOT)
            dayOfMonth.text = day.date.dayOfMonth.toString()
        }

        private fun handleSelection(day: Day) = binding.apply {
            val textAppearance = if (day.isSelected) {
                R.style.HorizontalCalendar_Text_SelectedDayOfWeek to R.style.HorizontalCalendar_Text_SelectedDayOfMonth
            } else {
                R.style.HorizontalCalendar_Text_DeselectedDayOfWeek to R.style.HorizontalCalendar_Text_DeselectedDayOfMonth
            }

            dayOfWeek.setTextAppearance(textAppearance.first)
            dayOfMonth.setTextAppearance(textAppearance.second)
            selectedBackground.isVisible = day.isSelected
        }
    }
}