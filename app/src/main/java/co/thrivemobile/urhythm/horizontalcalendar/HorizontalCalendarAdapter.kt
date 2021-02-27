package co.thrivemobile.urhythm.horizontalcalendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.thrivemobile.urhythm.databinding.ItemHorizontalCalendarDateBinding
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class HorizontalCalendarAdapter
    : PagedListAdapter<LocalDate, HorizontalCalendarAdapter.CalendarDateViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LocalDate>() {
            override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
                return oldItem.isEqual(newItem)
            }

            override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
                return oldItem.isEqual(newItem)
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
        }
    }

    class CalendarDateViewHolder(
        private val binding: ItemHorizontalCalendarDateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(date: LocalDate) {
            binding.apply {
                dayOfWeek.text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ROOT)
                dayOfMonth.text = date.dayOfMonth.toString()
            }
        }
    }
}