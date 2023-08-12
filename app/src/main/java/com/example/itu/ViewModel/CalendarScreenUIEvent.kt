package com.example.itu.ViewModel

import com.example.itu.Model.CalendarDate
import java.util.*

// Author: Matej Kocman

sealed class CalendarScreenUIEvent{
    data class OnSelectDay(val selectedDay: CalendarDate): CalendarScreenUIEvent()
}
