package com.example.itu.ViewModel

import com.example.itu.Model.CalendarDate
import com.example.itu.Model.Day

// Author: Jakub Drobena

data class TestInputScreenUiState(
    val dayToEdit: Day = Day(CalendarDate())
)