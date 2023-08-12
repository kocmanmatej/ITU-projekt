package com.example.itu.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itu.Model.CalendarDate
import com.example.itu.Model.Day
import com.example.itu.Model.Phase
import com.example.itu.Model.Repository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*

// Author: Matej Kocman

class CalendarScreenViewModel(
    private val repository: Repository
) : ViewModel(), DefaultLifecycleObserver {
    var uiState by mutableStateOf(CalendarScreenUiState())
        private set

    init {
        val calendar = Calendar.getInstance()

        uiState = uiState.copy(
            selectedDayDate =
            CalendarDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        )
    }

    override fun onStart(owner: LifecycleOwner) {
        getDaysInCalendar(uiState.selectedDayDate)
        getCategoryOrder()
        getCategoryColor()
        super.onResume(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        repository.saveDataAsJSON()
        super.onResume(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        repository.saveDataAsJSON()
        super.onResume(owner)
    }

    fun onEvent(event: CalendarScreenUIEvent) {
        when (event) {
            is CalendarScreenUIEvent.OnSelectDay -> {
                updateSelectedDay(event.selectedDay)
            }
        }
    }

    private fun updateSelectedDay(newSelectedDayDate: CalendarDate) {
        viewModelScope.launch {
            val oldSelectedDayDate = uiState.selectedDayDate

            if (newSelectedDayDate.month == 13) {
                newSelectedDayDate.month = 1
                newSelectedDayDate.year += 1
            }
            if (newSelectedDayDate.month == 0) {
                newSelectedDayDate.month = 12
                newSelectedDayDate.year -= 1
            }


            if (newSelectedDayDate.month ==
                oldSelectedDayDate.month
            ) {
                if (newSelectedDayDate.day ==
                    oldSelectedDayDate.day
                ) {
                    cancel()
                }
                uiState = uiState.copy(selectedDayDate = newSelectedDayDate)
                cancel()
            } else {
                getDaysInCalendar(newSelectedDayDate)
            }
        }
    }

    private fun getDaysInCalendar(selectedDayDate: CalendarDate) {
        val calendar = Calendar.getInstance()
        val dayList = ArrayList<Day>()
        val dayOfCycleList = ArrayList<String>()
        val todayDate = CalendarDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        calendar.set(selectedDayDate.year, selectedDayDate.month - 1, 1)
        val daysAfterSunday = calendar.get(Calendar.DAY_OF_WEEK) - 1
        calendar.add(Calendar.DAY_OF_MONTH, -daysAfterSunday)

        val firstDayOfCalendar = CalendarDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        var firstPeriodDayBefore = repository.getFirstPeriodDayBefore(firstDayOfCalendar)
        var firstPeriodDayAfter = repository.getFirstPeriodDayAfter(firstDayOfCalendar)

        while (dayList.size < 42) {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val date = CalendarDate(year, month, day)

            if (date == firstPeriodDayAfter) {
                firstPeriodDayBefore = firstPeriodDayAfter
                firstPeriodDayAfter = repository.getFirstPeriodDayAfter(firstPeriodDayAfter)
            }

            var phase: Phase? = null
            var dayOfCycle = if (firstPeriodDayBefore == null)
                "" else (getDayDiff(firstPeriodDayBefore, date) + 1).toString()

            if (firstPeriodDayAfter == null ) {
                if(dayOfCycle != ""){
                    dayOfCycle = (((dayOfCycle.toInt() - 1) % 28) + 1).toString()
                    if (dayOfCycle.toInt() in 11..17) {
                        phase = Phase.FERTILE
                    }
                    if (dayOfCycle.toInt() == 16) {
                        phase = Phase.OVULATING
                    }
                }
            } else {
                if (getDayDiff(date, firstPeriodDayAfter) in (12..18)) {
                    phase = Phase.FERTILE
                }
                if (getDayDiff(date, firstPeriodDayAfter) == 13) {
                    phase = Phase.OVULATING
                }
            }

            dayOfCycleList.add(dayOfCycle)

            val dayObj = repository.getDay(date)
            if (dayObj == null) {
                dayList.add(Day(date, phase))
            } else {
                if (dayObj.phase == null) {
                    dayObj.phase = phase
                }
                dayList.add(dayObj)
            }

            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        uiState = uiState.copy(
            daysInCalendar = dayList,
            selectedDayDate = selectedDayDate,
            todayDate = todayDate,
            daysOfCycle = dayOfCycleList
        )
    }

    private fun getDayDiff(earlierDay: CalendarDate, laterDay: CalendarDate): Int {
        val calendar = Calendar.getInstance()
        calendar.set(earlierDay.year, earlierDay.month - 1, earlierDay.day)
        val earlierDays = calendar.timeInMillis / 1000 / 60 / 60 / 24
        calendar.set(laterDay.year, laterDay.month - 1, laterDay.day)
        val laterDays = calendar.timeInMillis / 1000 / 60 / 60 / 24

        val diff = laterDays - earlierDays
        return diff.toInt()
    }

    private fun getCategoryOrder() {
        uiState = uiState.copy(categoryOrder = repository.getCategoryOrder())
    }

    private fun getCategoryColor() {
        uiState = uiState.copy(categoryColor = repository.getCategoryColor())
    }
}