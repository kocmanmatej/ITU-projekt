package com.example.itu.ViewModel

// Author: Jakub Drobena

sealed class DayDetailUiEvent {
    object OnAddAcne: DayDetailUiEvent()
    object OnAddBellyCramps: DayDetailUiEvent()
    object OnAddBloating: DayDetailUiEvent()
    object OnAddBreastPain: DayDetailUiEvent()
    object OnAddLowerBackPain: DayDetailUiEvent()
    object OnAddHeadAche: DayDetailUiEvent()
    object OnAddDiarrhea: DayDetailUiEvent()
    object OnAddInsomnia: DayDetailUiEvent()
    object OnAddGassiness: DayDetailUiEvent()
    object OnAddTiredness: DayDetailUiEvent()
    object OnAddCongestion: DayDetailUiEvent()
    object OnAddSpotting: DayDetailUiEvent()
    object OnAddSwearing: DayDetailUiEvent()
    object OnAddSex: DayDetailUiEvent()
    object OnAddSad: DayDetailUiEvent()
    object OnAddAngry: DayDetailUiEvent()
    object OnAddNormal: DayDetailUiEvent()
    object OnAddAngsty: DayDetailUiEvent()
    object OnAddDepressed: DayDetailUiEvent()
    object OnAddInLove: DayDetailUiEvent()
    object OnAddTired: DayDetailUiEvent()
    object OnAddEmotional: DayDetailUiEvent()
    object OnAddDimple : DayDetailUiEvent()
    object OnAddSwolen : DayDetailUiEvent()
    object OnAddFissures : DayDetailUiEvent()
    object OnAddDischarge : DayDetailUiEvent()
    object OnAddKnot : DayDetailUiEvent()
    object OnAddPain : DayDetailUiEvent()
    object OnAddCreamy : DayDetailUiEvent()
    object OnAddDry : DayDetailUiEvent()
    object OnAddEggWhite : DayDetailUiEvent()
    object OnAddSticky : DayDetailUiEvent()
    object OnAddWatery : DayDetailUiEvent()
    object OnAddUnusual : DayDetailUiEvent()
    object OnAddNone : DayDetailUiEvent()
    object OnAddPink : DayDetailUiEvent()
    object OnAddRed : DayDetailUiEvent()
    object OnAddYellow : DayDetailUiEvent()
    object OnAddOvulacia : DayDetailUiEvent() {

    }

    object OnAddOvulaciaNeg : DayDetailUiEvent() {

    }

    data class OnAddNote(val note : String) : DayDetailUiEvent()

}