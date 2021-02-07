package co.thrivemobile.urhythm.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.time.OffsetTime

@Entity(tableName = "entries")
data class Entry(
    @ColumnInfo(name = "date") val date: OffsetDateTime,
    @ColumnInfo(name = "time") val time: OffsetTime,
    @ColumnInfo(name = "energy") val energy: Int,
    @ColumnInfo(name = "focus") val focus: Int,
    @ColumnInfo(name = "motivation") val motivation: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}