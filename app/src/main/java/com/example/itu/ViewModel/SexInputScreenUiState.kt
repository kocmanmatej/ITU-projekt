package com.example.itu.ViewModel

import com.example.itu.Model.CalendarDate
import com.example.itu.Model.Day

// Author: Jakub Drobena

data class SexInputScreenUiState(
    val dayToEdit: Day = Day(CalendarDate())
)
