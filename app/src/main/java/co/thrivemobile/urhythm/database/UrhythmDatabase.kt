package co.thrivemobile.urhythm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Entry::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class UrhythmDatabase : RoomDatabase() {
    abstract fun getUrhythmDao(): UrhythmDao
}