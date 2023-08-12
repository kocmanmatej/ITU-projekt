package com.example.itu.ViewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itu.Model.*
import kotlinx.coroutines.launch

// Author: Jakub Drobena

class TesInputScreenViewModel(
    private val repository: Repository,
    initYear: Int,
    initMonth: Int,
    initDay: Int
) : ViewModel() {
    var uiState by mutableStateOf(TestInputScreenUiState())
        private set

    init {
        val selectedDay = CalendarDate(initYear, initMonth, initDay)
        val dayToEdit = repository.getDay(selectedDay)
        uiState = if (dayToEdit != null)
            uiState.copy(dayToEdit = dayToEdit)
        else
            uiState.copy(dayToEdit = Day(selectedDay))
    }
    fun onEvent(event: TestInputScreenUIEvent) {
        when (event) {
            is TestInputScreenUIEvent.OnAddOvulation -> {
                setOvulation()
            }
            is TestInputScreenUIEvent.OnAddOvulationNeg -> {
                setOvulationNeg()
            }
            is TestInputScreenUIEvent.OnGravidityPos ->
            {
                setGravidity(Gravidity.POSITIVE)
            }
            is TestInputScreenUIEvent.OnGravidityNeg ->
            {
                setGravidity(Gravidity.NEGATIVE)
            }
            is TestInputScreenUIEvent.OnGravidityFaint ->
            {
                setGravidity(Gravidity.FEINT_LINE)
            }
            is TestInputScreenUIEvent.OnFertilityHighest ->
            {
                setFertility(Fertility.HIGHEST)
            }
            is TestInputScreenUIEvent.OnFertilityHigh ->
            {
                setFertility(Fertility.HIGH)
            }
            is TestInputScreenUIEvent.OnFertilityLow ->
            {
                setFertility(Fertility.LOW)
            }

        }
    }
    private fun setOvulation()
    {
        repository.setOvulation(uiState.dayToEdit.date)
        val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
        uiState = TestInputScreenUiState()
        uiState = TestInputScreenUiState(editedDay)
    }
    private fun setOvulationNeg()
    {
        repository.setOvulationNeg(uiState.dayToEdit.date)
        val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
        uiState = TestInputScreenUiState()
        uiState = TestInputScreenUiState(editedDay)
    }
    private fun setGravidity(gravidity: Gravidity)
    {
        repository.setGravidity(gravidity,uiState.dayToEdit.date)
        val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
        uiState = TestInputScreenUiState()
        uiState = TestInputScreenUiState(editedDay)
    }
    private fun setFertility(fertility: Fertility)
    {
        repository.setFertility(fertility,uiState.dayToEdit.date)
        val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
        uiState = TestInputScreenUiState()
        uiState = TestInputScreenUiState(editedDay)
    }

}