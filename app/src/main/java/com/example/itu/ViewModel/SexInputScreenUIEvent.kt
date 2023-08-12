package com.example.itu.ViewModel

// Author: Jakub Drobena

sealed class SexInputScreenUIEvent {
    data class OnAddCondom(val index: Int): SexInputScreenUIEvent()
    data class OnAddPlan_B(val index: Int): SexInputScreenUIEvent()
    data class OnAddMasturbation(val index: Int): SexInputScreenUIEvent()
    data class OnAddOrgasm(val index: Int): SexInputScreenUIEvent()
    data class OnAddNote(val note: String, val index: Int): SexInputScreenUIEvent()

}