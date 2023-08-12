package com.example.itu.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itu.Model.CalendarDate
import com.example.itu.Model.Day
import com.example.itu.Model.Repository
import kotlinx.coroutines.launch

// Author: Jakub Drobena

class SexInputScreenViewModel(
    private val repository: Repository,
    initYear: Int,
    initMonth: Int,
    initDay: Int,
    index: Int
) : ViewModel() {
    var index :Int? = null
    var uiState by mutableStateOf(SexInputScreenUiState())
        private set

    init {
        this.index = index
        val selectedDay = CalendarDate(initYear, initMonth, initDay)
        val dayToEdit = repository.getDay(selectedDay)
        uiState = if (dayToEdit != null)
            uiState.copy(dayToEdit = dayToEdit)
        else
            uiState.copy(dayToEdit = Day(selectedDay))
    }

    fun onEvent(event: SexInputScreenUIEvent) {
        when (event) {
            is SexInputScreenUIEvent.OnAddCondom -> {
                setCondom(event.index)
            }
            is SexInputScreenUIEvent.OnAddMasturbation ->{
                setMasturbation(event.index)
            }
            is SexInputScreenUIEvent.OnAddPlan_B ->{
                setPlan_B(event.index)
            }
            is SexInputScreenUIEvent.OnAddOrgasm ->{
                setOrgasm(event.index)
            }
            is SexInputScreenUIEvent.OnAddNote ->{
                setNote(event.note,event.index)
            }

        }
    }
    private fun setCondom(index :Int)
    {
        repository.setCondom(index, uiState.dayToEdit.date)
        val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
        uiState = SexInputScreenUiState()
        uiState = SexInputScreenUiState(editedDay)
    }
    private fun setMasturbation(index :Int)
    {
        repository.setMasturbation(index, uiState.dayToEdit.date)
        val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
        uiState = SexInputScreenUiState()
        uiState = SexInputScreenUiState(editedDay)
    }
    private fun setOrgasm(index :Int)
    {
        repository.setOrgasm(index, uiState.dayToEdit.date)
        val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
        uiState = SexInputScreenUiState()
        uiState = SexInputScreenUiState(editedDay)
    }
    private fun setPlan_B(index :Int)
    {
        repository.setPlan_B(index, uiState.dayToEdit.date)
        val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
        uiState = SexInputScreenUiState()
        uiState = SexInputScreenUiState(editedDay)
    }
    private fun setNote(note :String, index :Int)
    {
        repository.setNote(note,index, uiState.dayToEdit.date)
        val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
        uiState = SexInputScreenUiState()
        uiState = SexInputScreenUiState(editedDay)
    }

}