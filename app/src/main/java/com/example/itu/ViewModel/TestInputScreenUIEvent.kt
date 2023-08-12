package com.example.itu.ViewModel

// Author: Jakub Drobena

sealed class TestInputScreenUIEvent {
    object OnAddOvulation:TestInputScreenUIEvent()
    object OnAddOvulationNeg : TestInputScreenUIEvent()
    object OnGravidityNeg : TestInputScreenUIEvent()
    object OnGravidityFaint : TestInputScreenUIEvent()
    object OnGravidityPos : TestInputScreenUIEvent()
    object OnFertilityHighest : TestInputScreenUIEvent() {

    }

    object OnFertilityHigh : TestInputScreenUIEvent() {

    }

    object OnFertilityLow : TestInputScreenUIEvent() {

    }
}