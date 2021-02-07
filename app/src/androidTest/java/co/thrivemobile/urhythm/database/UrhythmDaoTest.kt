package co.thrivemobile.urhythm.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZoneOffset

@RunWith(AndroidJUnit4::class)
class UrhythmDaoTest {

    private lateinit var dao: UrhythmDao
    private lateinit var db: UrhythmDatabase

    companion object {
        private val date1 = OffsetDateTime.of(2021, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC)
        private val date2 = OffsetDateTime.of(2021, 1, 2, 1, 0, 0, 0, ZoneOffset.UTC)
        private val time1 = OffsetTime.of(1, 0, 0, 0, ZoneOffset.UTC)
    }

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UrhythmDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.getUrhythmDao()
    }

    @After
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun entry_is_properly_inserted() = runBlocking {
        val entry = Entry(
            date = date1,
            time = time1,
            energy = 5,
            focus = 5,
            motivation = 5
        )
        dao.insertAll(entry)
        val entries = dao.getAllEntriesForDate(date1)
        assertThat(entries).contains(entry)
    }

    @Test
    fun ensure_we_only_get_entries_for_specific_date() = runBlocking {
        val entries = listOf(
            Entry(
                date = date1,
                time = time1,
                energy = 5,
                focus = 5,
                motivation = 5
            ),
            Entry(
                date = date1,
                time = time1.plusHours(1),
                energy = 5,
                focus = 5,
                motivation = 5
            ),
            Entry(
                date = date1,
                time = time1.plusHours(2),
                energy = 5,
                focus = 5,
                motivation = 5
            ),
            Entry(
                date = date2,
                time = time1,
                energy = 5,
                focus = 5,
                motivation = 5
            )
        )

        dao.insertAll(*entries.toTypedArray())

        val entriesForFirstDate = dao.getAllEntriesForDate(date1)
        assertAll {
            assertThat(entriesForFirstDate.size).isEqualTo(3)
            assertThat(entriesForFirstDate.take(3)).isEqualTo(entries.take(3))
        }

        val entriesForSecondDate = dao.getAllEntriesForDate(date2)
        assertAll {
            assertThat(entriesForSecondDate.size).isEqualTo(1)
            assertThat(entriesForSecondDate).containsOnly(entries.last())
        }
    }
}