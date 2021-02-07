package co.thrivemobile.urhythm.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.time.OffsetDateTime

@Dao
interface UrhythmDao {

    @Query("SELECT * FROM entries WHERE date IS :date")
    suspend fun getAllEntriesForDate(date: OffsetDateTime): List<Entry>

    @Insert
    suspend fun insertAll(vararg entry: Entry)
}