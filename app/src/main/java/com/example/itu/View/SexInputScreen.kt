package com.example.itu.View
import com.example.itu.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.itu.ViewModel.SexInputScreenUIEvent
import com.example.itu.ViewModel.SexInputScreenViewModel

// Author: Jakub Drobena

//View pre zadavanie záznamu o sexe
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SexInputScreen(viewModel: SexInputScreenViewModel = viewModel(), navigateToCalendarScreen: (Int, Int ,Int) -> Unit)
{
    Column(modifier = Modifier.fillMaxHeight()) {
        //Tlačítko návratu na predchádzajúcu obrazovku
        Box(
            modifier = Modifier.height(40.dp),
            contentAlignment = Alignment.TopStart
        )
        {
            Image(painter = painterResource(id = R.drawable.back), contentDescription = "Back Button", modifier = Modifier.clickable { navigateToCalendarScreen(viewModel.uiState.dayToEdit.date.year,viewModel.uiState.dayToEdit.date.month,viewModel.uiState.dayToEdit.date.day) })
        }
        //Zaklikávacie možnosti sexu
        LazyRow(modifier = Modifier.height(150.dp))
        {
            item{
                Item("Kondóm", painter = painterResource(id = R.drawable.condom), day = viewModel)
            }
            item{
                Item("Tabletka po", painter = painterResource(id = R.drawable.meds), day = viewModel)
            }
            item{
                Item("Orgazmus", painter = painterResource(id = R.drawable.happy), day = viewModel)
            }
            item{
                Item("Masturbácia", painter = painterResource(id = R.drawable.masturbator), day = viewModel)
            }

        }
        var text by remember { mutableStateOf(viewModel.uiState.dayToEdit.sex!![viewModel.index!!].note.orEmpty()) }
        val keyboardController = LocalSoftwareKeyboardController.current
        Box(modifier = Modifier.fillMaxWidth().height(80.dp))
        {
            //Pridávanie poznámky o sexe (Automaticky po ukončení zadavania textu)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        viewModel.onEvent(SexInputScreenUIEvent.OnAddNote(text, viewModel.index!!))

                    }),
                label = { Text(text = "Poznamka") },
                onValueChange = {
                    text = it
                })
        }

    }


}

//Zaklikávacie položky sexu
@Composable
fun Item(name : String, painter : Painter, modifier: Modifier = Modifier, day: SexInputScreenViewModel)
{
    var toggled : Boolean = false
    when (name)
    {
        "Kondóm"-> toggled = day.uiState.dayToEdit.sex!![day.index!!].condom == true
        "Tabletka po"-> toggled = day.uiState.dayToEdit.sex!![day.index!!].planB
        "Orgazmus" -> toggled = day.uiState.dayToEdit.sex!![day.index!!].orgasm == true
        "Masturbácia" -> toggled = day.uiState.dayToEdit.sex!![day.index!!].masturbation
    }
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    //Po kliknutí sa zavolá event ktorý zmenu zapíše do dát
                    when (name) {
                        "Tabletka po" -> day.onEvent(SexInputScreenUIEvent.OnAddPlan_B(day.index!!))
                        "Kondóm" -> day.onEvent(SexInputScreenUIEvent.OnAddCondom(day.index!!))
                        "Orgazmus" -> day.onEvent(SexInputScreenUIEvent.OnAddOrgasm(day.index!!))
                        "Masturbácia" -> {
                            day.onEvent(SexInputScreenUIEvent.OnAddMasturbation(day.index!!))
                        }
                        else -> {}
                    }
                }
                .padding(1.dp),
            shape = RoundedCornerShape(100.dp),
            backgroundColor = if(toggled) Color.Green else Color.Gray,
            elevation = 5.dp
        ) {
            Column(modifier = Modifier
                .height(130.dp)
                .width(130.dp))
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(2f)
                        .padding(12.dp),
                    contentAlignment = Alignment.TopCenter
                )
                {
                    Image(
                        modifier=Modifier.fillMaxSize(),
                        painter = painter,
                        contentDescription = "Icon",
                        contentScale = ContentScale.FillHeight
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.95f),
                    contentAlignment = Alignment.TopCenter
                )
                {
                    Text(name)
                }

            }
        }

    }
}