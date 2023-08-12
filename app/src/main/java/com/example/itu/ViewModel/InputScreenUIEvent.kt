package com.example.itu.ViewModel

import java.sql.Time

// Author: Jakub Drobena

sealed class InputScreenUIEvent {
    object OnAddSex: InputScreenUIEvent()
    object OnClickWater: InputScreenUIEvent()
    data class OnSetWeight(val kg: Float): InputScreenUIEvent()
    data class OnSetWater(val cups: Int): InputScreenUIEvent()
    data class OnSetTemp(val celsius: Float): InputScreenUIEvent()
    data class OnSetSleep(val startSleep: String, val endSleep :String): InputScreenUIEvent()
}