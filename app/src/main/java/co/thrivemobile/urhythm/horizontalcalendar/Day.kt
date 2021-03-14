package co.thrivemobile.urhythm.horizontalcalendar

import java.time.LocalDate

data class Day(
    var isSelected: Boolean = false,
    val date: LocalDate
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Day) {
            return false
        }
        return isSelected == other.isSelected &&
                date.isEqual(other.date)
    }

    override fun hashCode(): Int {
        return date.hashCode()
    }
}
