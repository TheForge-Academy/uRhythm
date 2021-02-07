package co.thrivemobile.urhythm.database

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZoneOffset

class ConvertersTest {

    companion object {
        private val date1 = OffsetDateTime.of(2021, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC)
        private val time1 = OffsetTime.of(1, 0, 0, 0, ZoneOffset.UTC)
    }

    @Test
    fun `when given a good date string it is converted to the correct OffsetDateTime`() {
        val stringToTest = "2021-01-01T01:00:00+00:00"
        val actualDate = Converters.toOffsetDateTime(stringToTest)
        assertThat(actualDate).isEqualTo(date1)
    }

    @Test
    fun `when given a null string it is converted to the current OffsetDateTime`() {
        val expectedDate = OffsetDateTime.now()
        val actualDate = Converters.toOffsetDateTime(null)
        assertAll {
            assertThat(expectedDate.year).isEqualTo(actualDate.year)
            assertThat(expectedDate.dayOfYear).isEqualTo(actualDate.dayOfYear)
        }
    }

    @Test
    fun `when given a good OffsetDateTime it is converted to the correct string`() {
        val expectedString = "2021-01-01T01:00:00Z"
        val actualDate = Converters.fromOffsetDateTime(date1)
        assertThat(actualDate).isEqualTo(expectedString)
    }

    @Test
    fun `when given a good time string it is converted to the correct OffsetDateTime`() {
        val stringToTest = "01:00:00+00:00"
        val actualDate = Converters.toOffsetTime(stringToTest)
        assertThat(actualDate).isEqualTo(time1)
    }

    @Test
    fun `when given a null string it is converted to the current OffsetTime`() {
        val expectedTime = OffsetTime.now()
        val actualTime = Converters.toOffsetTime(null)
        assertAll {
            assertThat(expectedTime.hour).isEqualTo(actualTime.hour)
            assertThat(expectedTime.minute).isEqualTo(actualTime.minute)
            assertThat(expectedTime.second).isEqualTo(actualTime.second)
        }
    }

    @Test
    fun `when given a good OffsetTime it is converted to the correct string`() {
        val expectedString = "01:00:00Z"
        val actualTime = Converters.fromOffsetDateTime(time1)
        assertThat(actualTime).isEqualTo(expectedString)
    }
}