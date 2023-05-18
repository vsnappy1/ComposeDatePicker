package com.vsnappy1.timepicker.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vsnappy1.timepicker.data.model.ComposeTimePickerTime
import com.vsnappy1.timepicker.enums.MinuteGap
import com.vsnappy1.timepicker.enums.TimeOfDay
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TimePickerViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TimePickerViewModel

    @Before
    fun setup() {
        viewModel = TimePickerViewModel()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_itIs24HourClockAndHourIsLessThanZero_shouldThrowException() {
        //Given
        val time = ComposeTimePickerTime.TwentyFourHourTime(-1, 45)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_itIs24HourClockAndHourIsMoreThan23_shouldThrowException() {
        //Given
        val time = ComposeTimePickerTime.TwentyFourHourTime(24, 45)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_itIs24HourClockAndMinuteIsLessThanZero_shouldThrowException() {
        //Given
        val time = ComposeTimePickerTime.TwentyFourHourTime(12, -1)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_itIs24HourClockAndMinuteIsMoreThan59_shouldThrowException() {
        //Given
        val time = ComposeTimePickerTime.TwentyFourHourTime(12, 61)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_itIs24HourClockAndMinuteIsNotMultipleOfGivenMinuteGap_shouldThrowException() {
        //Given
        val time = ComposeTimePickerTime.TwentyFourHourTime(12, 27)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_itIs12HourClockAndHourIsLessThanZero_shouldThrowException() {
        //Given
        val time = ComposeTimePickerTime.TwelveHourTime(-1, 45, TimeOfDay.AM)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_itIs12HourClockAndHourIsMoreThan12_shouldThrowException() {
        //Given
        val time = ComposeTimePickerTime.TwelveHourTime(13, 45, TimeOfDay.AM)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_itIs12HourClockAndMinuteIsLessThanZero_shouldThrowException() {
        //Given
        val time = ComposeTimePickerTime.TwelveHourTime(12, -1, TimeOfDay.AM)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_itIs12HourClockAndMinuteIsMoreThan59_shouldThrowException() {
        //Given
        val time = ComposeTimePickerTime.TwelveHourTime(12, 61, TimeOfDay.AM)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_itIs12HourClockAndMinuteIsNotMultipleOfGivenMinuteGap_shouldThrowException() {
        //Given
        val time = ComposeTimePickerTime.TwelveHourTime(12, 27, TimeOfDay.AM)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE)
    }

    @Test
    fun getUiStateTimeProvided_when_itIsAValid12HourClock_shouldUpdateTheUiStateAccordingly() {
        //Given
        val time = ComposeTimePickerTime.TwelveHourTime(12, 55, TimeOfDay.AM)

        //When
        viewModel.updateUiState(time, MinuteGap.FIVE)

        //Then
        (viewModel.getSelectedTime() as ComposeTimePickerTime.TwelveHourTime).let {
            assertEquals(12, it.hour)
            assertEquals(55, it.minute)
            assertEquals(TimeOfDay.AM, it.timeOfDay)
        }
    }

    @Test
    fun getUiStateTimeProvided_when_itIsAValid24HourClock_shouldUpdateTheUiStateAccordingly() {
        //Given
        val time = ComposeTimePickerTime.TwentyFourHourTime(12, 35)

        //When
        viewModel.updateUiState(time, MinuteGap.FIVE)

        //Then
        (viewModel.getSelectedTime() as ComposeTimePickerTime.TwentyFourHourTime).let {
            assertEquals(12, it.hour)
            assertEquals(35, it.minute)
        }
    }


}