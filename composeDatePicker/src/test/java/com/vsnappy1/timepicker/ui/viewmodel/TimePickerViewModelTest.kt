package com.vsnappy1.timepicker.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vsnappy1.MainCoroutineRule
import com.vsnappy1.timepicker.data.model.TimePickerTime
import com.vsnappy1.timepicker.enums.MinuteGap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
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

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: TimePickerViewModel

    @Before
    fun setup() {
        viewModel = TimePickerViewModel()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_hourIsLessThanZero_shouldThrowException() {
        //Given
        val time = TimePickerTime(-1, 45)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE, true)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_hourIsMoreThan23_shouldThrowException() {
        //Given
        val time = TimePickerTime(24, 45)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE, true)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_minuteIsLessThanZero_shouldThrowException() {
        //Given
        val time = TimePickerTime(12, -1)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE, true)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUiStateTimeProvided_when_minuteIsMoreThan59_shouldThrowException() {
        //Given
        val time = TimePickerTime(12, 60)

        //When
        viewModel.getUiStateTimeProvided(time, MinuteGap.FIVE, true)
    }


    @Test
    fun getUiStateTimeProvided_when_is24HourIsFalse_shouldUpdateTheUiStateAccordingly() {
        //Given
        val time = TimePickerTime(20, 55)

        //When
        viewModel.updateUiState(time, MinuteGap.FIVE, false)

        //Then
        viewModel.uiState.value?.let {
            val hour = it.hours[it.selectedHourIndex].toInt()
            val minute = it.minutes[it.selectedMinuteIndex].toInt()
            assertEquals(8, hour)
            assertEquals(55, minute)
            assertEquals(1, it.selectedTimeOfDayIndex)
        }
    }

    @Test
    fun getUiStateTimeProvided_when_is24HourIsTrue_shouldUpdateTheUiStateAccordingly() {
        //Given
        val time = TimePickerTime(20, 55)

        //When
        viewModel.updateUiState(time, MinuteGap.FIVE, true)

        //Then
        viewModel.uiState.value?.let {
            val hour = it.hours[it.selectedHourIndex].toInt()
            val minute = it.minutes[it.selectedMinuteIndex].toInt()
            assertEquals(20, hour)
            assertEquals(55, minute)
        }
    }

    @Test
    fun getUiStateTimeProvided_when_minuteProvidedIsNotMultipleOfMinuteGap_shouldUpdateTheUiStateAccordingly() {
        //Given
        val time = TimePickerTime(20, 51)

        //When
        viewModel.updateUiState(time, MinuteGap.FIVE, true)

        //Then
        viewModel.uiState.value?.let {
            val hour = it.hours[it.selectedHourIndex].toInt()
            val minute = it.minutes[it.selectedMinuteIndex].toInt()
            assertEquals(20, hour)
            assertEquals(55, minute)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateSelectedHourIndex_when_13HoursAdded_shouldChangeTimeOfDay() = runTest{
        //Given
        val time = TimePickerTime(0, 55)

        //When
        viewModel.updateUiState(time, MinuteGap.FIVE, false)
        viewModel.uiState.value?.let {
            viewModel.updateSelectedHourIndex(it.selectedHourIndex + 13)
        }
        advanceTimeBy(250)

        //Then
        viewModel.uiState.value?.let {
            assertEquals(1, it.selectedTimeOfDayIndex)
        }
    }

    @Test
    fun getSelectedTime_when_is24HourIsFalse_shouldReturnCorrectHoursAndMinutes() {
        //Given
        val time = TimePickerTime(20, 55)

        //When
        viewModel.updateUiState(time, MinuteGap.FIVE, false)

        //Then
        viewModel.getSelectedTime()?.let {
            assertEquals(20, it.hour)
            assertEquals(55, it.minute)
        }
    }

    @Test
    fun getSelectedTime_when_minuteGapIsFive_shouldCeilTheMinuteToNearestMultipleOfMinuteGap() {
        //Given
        val time = TimePickerTime(20, 53)

        //When
        viewModel.updateUiState(time, MinuteGap.FIVE, false)

        //Then
        viewModel.getSelectedTime()?.let {
            assertEquals(20, it.hour)
            assertEquals(55, it.minute)
        }
    }

    @Test
    fun getSelectedTime_when_minuteGapIsFiveAndCurrentMinuteIsMoreThan55_shouldMakeTheMinuteZeroAndIncrementTheHour() {
        //Given
        val time = TimePickerTime(20, 57)

        //When
        viewModel.updateUiState(time, MinuteGap.FIVE, false)

        //Then
        viewModel.getSelectedTime()?.let {
            assertEquals(21, it.hour)
            assertEquals(0, it.minute)
        }
    }

    @Test
    fun getSelectedTime_when_minuteGapIsFiveItIsElevenOClockAMAndCurrentMinuteIsMoreThan55_shouldMakeTheMinuteZeroAndIncrementTheHourAndChangeTimeOfTheDay() {
        //Given
        val time = TimePickerTime(11, 57)

        //When
        viewModel.updateUiState(time, MinuteGap.FIVE, false)

        //Then
        viewModel.getSelectedTime()?.let {
            assertEquals(12, it.hour)
            assertEquals(0, it.minute)
        }
        viewModel.uiState.value?.let {
            assertEquals(1, it.selectedTimeOfDayIndex)
        }
    }
    @Test
    fun getSelectedTime_when_minuteGapIsFiveItIsElevenOClockPMAndCurrentMinuteIsMoreThan55_shouldMakeTheMinuteZeroAndIncrementTheHourAndChangeTimeOfTheDay() {
        //Given
        val time = TimePickerTime(23, 57)

        //When
        viewModel.updateUiState(time, MinuteGap.FIVE, false)

        //Then
        viewModel.getSelectedTime()?.let {
            assertEquals(0, it.hour)
            assertEquals(0, it.minute)
        }
        viewModel.uiState.value?.let {
            assertEquals(0, it.selectedTimeOfDayIndex)
        }
    }
}