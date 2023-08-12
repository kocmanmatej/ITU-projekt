package com.example.itu.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itu.Model.*
import kotlinx.coroutines.launch

// Author: Jakub Drobena

class DayDetailViewModel(private val repository: Repository,
                         initYear: Int,
                         initMonth: Int,
                         initDay: Int,): ViewModel()
{
    var uiState by mutableStateOf(DayDetailUiState())
        private set
    init {
        val selectedDay = CalendarDate(initYear, initMonth, initDay)
        val dayToEdit = repository.getDay(selectedDay)
        uiState = if (dayToEdit != null)
            uiState.copy(dayToEdit = dayToEdit)
        else
            uiState.copy(dayToEdit = Day(selectedDay))
    }

    fun onEvent(event: DayDetailUiEvent) {
        when (event) {
            is DayDetailUiEvent.OnAddAcne -> {
                setSymptom(symptoms = Symptom.ACNE)
            }
            is DayDetailUiEvent.OnAddBellyCramps ->{
                setSymptom(symptoms = Symptom.BELLY_CRAMPS)
            }
            is DayDetailUiEvent.OnAddBloating ->{
                setSymptom(symptoms = Symptom.BLOATING)
            }
            is DayDetailUiEvent.OnAddBreastPain ->{
                setSymptom(symptoms = Symptom.BREAST_PAIN)
            }
            is DayDetailUiEvent.OnAddDiarrhea ->
            {
                setSymptom(symptoms = Symptom.DIARRHEA)
            }
            is DayDetailUiEvent.OnAddLowerBackPain ->
            {
                setSymptom(symptoms = Symptom.LOWER_BACK_PAIN)
            }
            is DayDetailUiEvent.OnAddHeadAche ->
            {
                setSymptom(symptoms = Symptom.HEADACHE)
            }
            is DayDetailUiEvent.OnAddGassiness ->
            {
                setSymptom(symptoms = Symptom.GASSINESS)
            }
            is DayDetailUiEvent.OnAddTiredness ->
            {
                setSymptom(symptoms = Symptom.TIREDNESS)
            }
            is DayDetailUiEvent.OnAddCongestion ->
            {
                setSymptom(symptoms = Symptom.CONGESTION)
            }
            is DayDetailUiEvent.OnAddInsomnia ->
            {
                setSymptom(symptoms = Symptom.INSOMNIA)
            }
            is DayDetailUiEvent.OnAddSpotting ->
            {
                setSymptom(symptoms = Symptom.SPOTTING)
            }
            is DayDetailUiEvent.OnAddSwearing ->
            {
                setSymptom(symptoms = Symptom.SWEARING)
            }
            is DayDetailUiEvent.OnAddSex ->
            {
                addSex()
            }
            is DayDetailUiEvent.OnAddNormal ->
            {
                setMood(Mood.NORMAL)
            }
            is DayDetailUiEvent.OnAddAngry ->
            {
                setMood(Mood.ANGRY)
            }
            is DayDetailUiEvent.OnAddAngsty ->
            {
                setMood(Mood.ANGSTY)
            }
            is DayDetailUiEvent.OnAddDepressed ->
            {
                setMood(Mood.DEPRESSED)
            }
            is DayDetailUiEvent.OnAddInLove ->
            {
                setMood(Mood.IN_LOVE)
            }
            is DayDetailUiEvent.OnAddSad ->
            {
                setMood(Mood.SAD)
            }
            is DayDetailUiEvent.OnAddTired ->
            {
                setMood(Mood.TIRED)
            }
            is DayDetailUiEvent.OnAddEmotional ->
            {
                setMood(Mood.EMOTIONAL)
            }
            is DayDetailUiEvent.OnAddPain ->
            {
                setBreast(Breasts.PAIN)
            }
            is DayDetailUiEvent.OnAddKnot ->
            {
                setBreast(Breasts.KNOT)
            }
            is DayDetailUiEvent.OnAddSwolen ->
            {
                setBreast(Breasts.SWOLLEN)
            }
            is DayDetailUiEvent.OnAddDischarge ->
            {
                setBreast(Breasts.DISCHARGE)
            }
            is DayDetailUiEvent.OnAddFissures ->
            {
                setBreast(Breasts.FISSURES)
            }
            is DayDetailUiEvent.OnAddDimple ->
            {
                setBreast(Breasts.DIMPLE)
            }
            is DayDetailUiEvent.OnAddCreamy ->
            {
                setMucus(Mucus.CREAMY)
            }
            is DayDetailUiEvent.OnAddDry ->
            {
                setMucus(Mucus.DRY)
            }
            is DayDetailUiEvent.OnAddEggWhite ->
            {
                setMucus(Mucus.EGGWHITE)
            }
            is DayDetailUiEvent.OnAddSticky ->
            {
                setMucus(Mucus.STICKY)
            }
            is DayDetailUiEvent.OnAddWatery ->
            {
                setMucus(Mucus.WATERY)
            }
            is DayDetailUiEvent.OnAddUnusual ->
            {
                setMucus(Mucus.UNUSUAL)
            }
            is DayDetailUiEvent.OnAddNone ->
            {
                setLochia(Lochia.NONE)
            }
            is DayDetailUiEvent.OnAddPink ->
            {
                setLochia(Lochia.PINK)
            }
            is DayDetailUiEvent.OnAddRed ->
            {
                setLochia(Lochia.RED)
            }
            is DayDetailUiEvent.OnAddYellow ->
            {
                setLochia(Lochia.YELLOW)
            }
            is DayDetailUiEvent.OnAddNote ->
            {
                setNote(event.note)
            }

        }
    }

    private fun setSymptom(symptoms: Symptom) {
        viewModelScope.launch {
            repository.setSymptom(symptoms,uiState.dayToEdit.date)
            val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
            uiState = DayDetailUiState()
            uiState = DayDetailUiState(editedDay)
        }
    }
    private fun setMood(mood: Mood) {
        viewModelScope.launch {
            repository.setMood(mood,uiState.dayToEdit.date)
            val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
            uiState = DayDetailUiState()
            uiState = DayDetailUiState(editedDay)
        }
    }

    private fun addSex()
    {
        viewModelScope.launch {
            repository.addSex(uiState.dayToEdit.date)
            val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
            uiState = DayDetailUiState()
            uiState = DayDetailUiState(editedDay)
        }
    }
    private fun setBreast(breasts: Breasts) {
        viewModelScope.launch {
            repository.setBreast(breasts,uiState.dayToEdit.date)
            val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
            uiState = DayDetailUiState()
            uiState = DayDetailUiState(editedDay)
        }
    }
    private fun setMucus(mucus: Mucus) {
        viewModelScope.launch {
            repository.setMucus(mucus,uiState.dayToEdit.date)
            val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
            uiState = DayDetailUiState()
            uiState = DayDetailUiState(editedDay)
        }
    }
    private fun setLochia(lochia: Lochia) {
        viewModelScope.launch {
            repository.setLochia(lochia,uiState.dayToEdit.date)
            val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
            uiState = DayDetailUiState()
            uiState = DayDetailUiState(editedDay)
        }
    }
    private fun setNote(note: String) {
        viewModelScope.launch {
            repository.setDayNote(note,uiState.dayToEdit.date)
            val editedDay = repository.getDay(uiState.dayToEdit.date) as Day
            uiState = DayDetailUiState()
            uiState = DayDetailUiState(editedDay)
        }
    }


}