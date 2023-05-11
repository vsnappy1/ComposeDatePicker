package com.vsnappy1.datepicker.ui.viewmodel

import android.icu.util.Calendar
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vsnappy1.MainCoroutineRule
import com.vsnappy1.datepicker.data.Constant
import com.vsnappy1.datepicker.data.model.ComposeDatePickerDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DatePickerViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DatePickerViewModel

    @Mock
    private lateinit var calendar: Calendar

    @Before
    fun setup() {
        viewModel = DatePickerViewModel()
    }

    @Test
    fun moveToNextMonth_when_selectedMonthIsNotDecember_yearShouldBeSame() {
        //Given
        viewModel.updateSelectedMonthIndex(0)
        val year = viewModel.uiState.value?.selectedYear

        //When
        viewModel.moveToNextMonth()

        //Then
        assertEquals(year, viewModel.uiState.value?.selectedYear)
    }

    @Test
    fun moveToNextMonth_when_selectedMonthIsDecember_yearShouldBeNextYear() {
        //Given
        viewModel.updateSelectedMonthIndex(11)
        val year = viewModel.uiState.value?.selectedYear

        //When
        viewModel.moveToNextMonth()

        //Then
        assertEquals(year?.plus(1), viewModel.uiState.value?.selectedYear)
    }

    @Test
    fun moveToNextMonth_when_selectedMonthIsDecemberAndYearIsTheLastYearInList_yearAndMonthShouldBeSame() {
        //Given
        viewModel.updateSelectedMonthIndex(11)
        viewModel.updateSelectedYearIndex(Constant.years.size - 1)
        val month = viewModel.uiState.value?.selectedMonth
        val year = viewModel.uiState.value?.selectedYear

        //When
        viewModel.moveToNextMonth()

        //Then
        assertEquals(year, viewModel.uiState.value?.selectedYear)
        assertEquals(month, viewModel.uiState.value?.selectedMonth)
    }

    @Test
    fun moveToPreviousMonth_when_selectedMonthIsNotJanuary_yearShouldBeSame() {
        //Given
        viewModel.updateSelectedMonthIndex(11)
        val year = viewModel.uiState.value?.selectedYear

        //When
        viewModel.moveToPreviousMonth()

        //Then
        assertEquals(year, viewModel.uiState.value?.selectedYear)
    }

    @Test
    fun moveToPreviousMonth_when_selectedMonthIsJanuary_yearShouldBePreviousYear() {
        //Given
        viewModel.updateSelectedMonthIndex(0)
        val year = viewModel.uiState.value?.selectedYear

        //When
        viewModel.moveToPreviousMonth()

        //Then
        assertEquals(year?.minus(1), viewModel.uiState.value?.selectedYear)
    }

    @Test
    fun moveToPreviousMonth_when_selectedMonthIsJanuaryAndYearIsTheFirstYearInList_yearAndMonthShouldBeSame() {
        //Given
        viewModel.updateSelectedMonthIndex(0)
        viewModel.updateSelectedYearIndex(0)
        val month = viewModel.uiState.value?.selectedMonth
        val year = viewModel.uiState.value?.selectedYear

        //When
        viewModel.moveToPreviousMonth()

        //Then
        assertEquals(year, viewModel.uiState.value?.selectedYear)
        assertEquals(month, viewModel.uiState.value?.selectedMonth)
    }

    @Test
    fun updateSelectedYearIndex_when_indexIsWithinRange_shouldUpdateTheSelectedYearAndCurrentVisibleMonth() {
        //Given
        viewModel.updateSelectedMonthIndex(0)
        viewModel.updateSelectedYearIndex(0)
        val currentVisibleMonth = viewModel.uiState.value?.currentVisibleMonth
        val year = viewModel.uiState.value?.selectedYear

        //When
        viewModel.updateSelectedYearIndex(5)

        //Then
        viewModel.uiState.value?.apply {
            assertEquals(currentVisibleMonth?.name, this.currentVisibleMonth.name)
            assertEquals(year?.plus(5), this.selectedYear)
            assertNotEquals(currentVisibleMonth, this.currentVisibleMonth)
        }
    }

    @Test
    fun toggleIsMonthYearViewVisible_when_isMonthYearViewVisibleIsFalse_shouldMakeIsMonthYearViewVisibleToTrue() {
        //Given
        viewModel.uiState.value?.copy(isMonthYearViewVisible = false)
            ?.let { viewModel.updateUiState(it) }

        //When
        viewModel.toggleIsMonthYearViewVisible()

        //Then
        assertTrue(viewModel.uiState.value?.isMonthYearViewVisible == true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toggleIsMonthYearViewVisible_when_isMonthYearViewVisibleIsTrue_shouldMakeIsMonthYearViewVisibleToFalseAndUpdateSelectedMonthIndex() =
        runTest {
            //Given
            viewModel.uiState.value?.copy(isMonthYearViewVisible = true)
                ?.let {
                    viewModel.updateUiState(it)
                    viewModel.updateSelectedMonthIndex(5)
                }

            //When
            viewModel.toggleIsMonthYearViewVisible()
            advanceTimeBy(251)

            //Then
            assertTrue(viewModel.uiState.value?.isMonthYearViewVisible == false)
            assertEquals(
                Constant.monthMiddleIndex + 5,
                viewModel.uiState.value?.selectedMonthIndex,
            )
        }

    @Test(expected = IllegalArgumentException::class)
    fun setDate_when_givenYearIsNotWithinRange_shouldThrowAndIllegalArgumentException() {
        //Given
        val lastYear = Constant.years.last()
        val composeDatePickerDate = ComposeDatePickerDate(lastYear + 1, 0, 1)

        //When
        viewModel.setDate(composeDatePickerDate, calendar = calendar)
    }

    @Test(expected = IllegalArgumentException::class)
    fun setDate_when_givenMonthIsNotWithinRange_shouldThrowAndIllegalArgumentException() {
        //Given
        val lastYear = Constant.years.last()
        val composeDatePickerDate = ComposeDatePickerDate(lastYear, 12, 1)

        //When
        viewModel.setDate(composeDatePickerDate, calendar = calendar)
    }

    @Test(expected = IllegalArgumentException::class)
    fun setDate_when_givenDayIsZero_shouldThrowAndIllegalArgumentException() {
        //Given
        val lastYear = Constant.years.last()
        val composeDatePickerDate = ComposeDatePickerDate(lastYear, 5, 0)

        //When
        viewModel.setDate(composeDatePickerDate, calendar = calendar)
    }

    @Test(expected = IllegalArgumentException::class)
    fun setDate_when_givenDayIsMoreThenMaxDayOfMonth_shouldThrowAndIllegalArgumentException() {
        //Given
        val lastYear = Constant.years.last()
        val composeDatePickerDate = ComposeDatePickerDate(lastYear, 5, 31)

        //When
        viewModel.setDate(composeDatePickerDate, calendar = calendar)
    }

    @Test
    fun setDate_when_givenDateIsWithinRange_shouldUpdateTheSelectedYearSelectedMonthAndSelectedDay() {
        //Given
        viewModel.updateSelectedDayAndMonth(1)
        viewModel.updateSelectedMonthIndex(0)
        viewModel.updateSelectedYearIndex(0)

        val day = 7
        val month = 5
        val year = Constant.years.last()
        val composeDatePickerDate = ComposeDatePickerDate(year, month, day)

        Mockito.`when`(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).thenReturn(30)

        //When
        viewModel.setDate(composeDatePickerDate, calendar = calendar)

        //Then
        viewModel.uiState.value?.let {
            assertEquals(day, it.selectedDayOfMonth)
            assertEquals(month, it.selectedMonth.number)
            assertEquals(year, it.selectedYear)
        }
    }
}