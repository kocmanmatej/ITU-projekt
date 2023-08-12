package com.example.itu.ViewModel

import androidx.compose.ui.graphics.Color
import com.example.itu.Model.CalendarDate
import com.example.itu.Model.Category
import com.example.itu.Model.Day
import java.util.*

// Author: Matej Kocman

data class CalendarScreenUiState(
    val daysInCalendar: ArrayList<Day> = ArrayList<Day>(),
    val daysOfCycle: ArrayList<String> = ArrayList<String>(),
    val selectedDayDate: CalendarDate = CalendarDate(),
    val todayDate: CalendarDate = CalendarDate(),
    val categoryOrder: MutableList<Category> = mutableListOf(),
    val categoryColor: Map<Category, Color> = mapOf()
)


