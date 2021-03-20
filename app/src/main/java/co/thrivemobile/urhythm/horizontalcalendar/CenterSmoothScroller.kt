package co.thrivemobile.urhythm.horizontalcalendar

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
    override fun calculateDtToFit(
        viewStart: Int,
        viewEnd: Int,
        boxStart: Int,
        boxEnd: Int,
        snapPreference: Int
    ): Int {
        val boxCenter = (boxEnd - boxStart) / 2
        val boxScreenCenter = boxStart + boxCenter
        val viewCenter = (viewEnd - viewStart) / 2
        val viewScreenCenter = viewStart + viewCenter
        return boxScreenCenter - viewScreenCenter
    }
}