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

class InputScreenViewModel(
    private val repository: Repository,
    initYear: Int,
    initMonth: Int,
    initDay: Int,
    input: String?
) : ViewModel() {
    var inputType :String = ""
    var uiState by mutableStateOf(InputScreenUiState())
        private set

    init {
        if (input != null) {
            inputType = input
        }
        val selectedDay = CalendarDate(initYear, initMonth, initDay)
        val dayToEdit = repository.getDay(selectedDay)
        uiState = if (dayToEdit != null)
            uiState.copy(dayToEdit = dayToEdit)
        else
            uiState.copy(dayToEdit = Day(selectedDay))
    }

    fun onEvent(event: InputScreenUIEvent) {
        when (event) {
            is InputScreenUIEvent.OnSetWeight -> {
                setWeight(event.kg)
            }
            is InputScreenUIEvent.OnSetWater -> {
                setWater(event.cups)
            }
            is InputScreenUIEvent.OnAddSex -> {
                // do stuff, change uiState
            }
            is InputScreenUIEvent.OnSetTemp -> {
                setTemp(event.celsius)
            }
            is InputScreenUIEvent.OnSetSleep -> {
                setSleep(event.startSleep,event.endSleep)
            }
        }
    }

    private fun setWeight(kg: Float) {
        // ak to chapem spravne, tieto viewmodel funkcie co robia business logic
        // by si mal zaobalit do tohto launch aby sa spustili ako coroutines
        // korutiny bezia na inom threade ako main, takze napr ui dalej reaguje
        // a nezmrzne kym sa robi tato funkcia, lebo nie su na ronvakom threade
        viewModelScope.launch {
            repository.setWeight(kg, uiState.dayToEdit.date)
            // viewmodel by mal zmenit repozitar a potom sa repozitara spytat na novy udaj
            // aby si zarucil Single Source Of Truth, ktorym je repozitar
            // ak by si dal repozitaru ze zmen udaj a potom si dalsim prikazom priamo zmenil
            // uiState tak uiState moze byt zmeneny skor ako repozitar a mal by si 2 Source Of Truth
            // takze asi takto to je korektne idk
            val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
            uiState = InputScreenUiState()
            uiState = InputScreenUiState(editedDay)
            // musim ho najskor takto vynulovat akoby, pretoze inak si uiState mysli ze sa nezmenil,
            // pretoze uiState nevidi do vnutra vnorenych objektov, nevidi zmeny v sex,lifestyle,atd..
        }
    }
    private fun setWater(cups: Int) {
        viewModelScope.launch {
            repository.setWater(cups, uiState.dayToEdit.date)
            val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
            uiState = InputScreenUiState()
            uiState = InputScreenUiState(editedDay)
        }
    }
    private fun setTemp(celsius: Float) {
        viewModelScope.launch {
            repository.setTemp(celsius, uiState.dayToEdit.date)
            val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
            uiState = InputScreenUiState()
            uiState = InputScreenUiState(editedDay)
        }
    }
    private fun setSleep(startSleep: String, endSleep:String) {
        viewModelScope.launch {
            repository.setSleep(startSleep,endSleep,uiState.dayToEdit.date)
            val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
            uiState = InputScreenUiState()
            uiState = InputScreenUiState(editedDay)
        }
    }
}