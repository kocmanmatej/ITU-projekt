package com.example.itu.View

import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.itu.R
import com.example.itu.ViewModel.InputScreenUIEvent
import com.example.itu.ViewModel.InputScreenViewModel
import java.util.*

// Author: Jakub Drobena

@OptIn(ExperimentalComposeUiApi::class)
@Composable
//Hlavné view Obrazovky InputScreen
fun InputScreen(
    viewModel: InputScreenViewModel = viewModel(), navigateToCalendarScreen: (Int, Int ,Int) -> Unit
) {
    Column(modifier = Modifier.height(100.dp)) {
        //Tlačítko navratu na predchádzajúcu obrazovku
        Box(
            modifier = Modifier.height(40.dp),
            contentAlignment = Alignment.TopStart
        )
        {
            Image(painter = painterResource(id = R.drawable.back), contentDescription = "Back Button", modifier = Modifier.clickable { navigateToCalendarScreen(viewModel.uiState.dayToEdit.date.year,viewModel.uiState.dayToEdit.date.month,viewModel.uiState.dayToEdit.date.day) })
        }
        //Vybraný den pre vloženie životného štýlu
        Text(text = ""+
                viewModel.uiState.dayToEdit.date.day + "." +
                viewModel.uiState.dayToEdit.date.month + "." +
                viewModel.uiState.dayToEdit.date.year, modifier = Modifier.height(30.dp))
    }
    //Switch na vybratie zadavaného životného štýlu
    when(viewModel.inputType)
    {
        "Váha"-> {
            WeightInput(navigateToCalendarScreen = navigateToCalendarScreen)
        }
        "Voda" -> {
            DrinkInput(navigateToCalendarScreen = navigateToCalendarScreen)
        }
        "Teplota" -> {
            TemperatureInput(navigateToCalendarScreen = navigateToCalendarScreen)
        }
        "Spánok" -> {
            SleepInput(navigateToCalendarScreen = navigateToCalendarScreen)
        }
        else->
        {
            Text(text = "404 NOT FOUND")}
    }
    
}

//Zadavanie hmotnosti
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeightInput( viewModel: InputScreenViewModel = viewModel(), navigateToCalendarScreen: (Int, Int ,Int) -> Unit)
{
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        //Pokial bola zadaná už nejaká váha zobrazí sa
        if (viewModel.uiState.dayToEdit.lifestyle != null &&
            viewModel.uiState.dayToEdit.lifestyle!!.weight != null
        ) {
            Text(text = "Tvoja aktuálna váha je: " +
                    viewModel.uiState.dayToEdit.lifestyle!!.weight.toString()+ " Kg")
        }

        var text by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        //Políčko na zadanie váhy
        OutlinedTextField(
            value = text,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
            ),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}),
            label = { Text(text = "Zadaj váhu") },
            onValueChange = {
                text = it.replace(",",".")
            }
        )
        //Potvrdzovacie tlačítko
        Button(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
                .height(35.dp),
            elevation = null,
            shape = RoundedCornerShape(30),
            onClick = { viewModel.onEvent(InputScreenUIEvent.OnSetWeight(text.toFloat())) },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.lightpink))
        ) {
            Text(
                text = "Potvrdit vahu",
                color = colorResource(R.color.pink),
                letterSpacing = 0.1.sp,
                fontSize = 13.sp,
                fontWeight = FontWeight(800),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}


@Composable
fun DrinkInput(viewModel: InputScreenViewModel = viewModel(), navigateToCalendarScreen: (Int, Int ,Int) -> Unit)
{
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        //Pokial nebola pridana ziadna voda tak sa zobrazí 0 inak sa zobrazý aktualny počet vypytích pohárov
        var water = 0
        if(viewModel.uiState.dayToEdit.lifestyle?.water != null)
            if(viewModel.uiState.dayToEdit.lifestyle?.water != null)
                water =viewModel.uiState.dayToEdit.lifestyle?.water!!
        //Odobratie pohára vody funguje ak už bol vypitý nejaký pohár
        Button(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
                .height(35.dp),
            elevation = null,
            shape = RoundedCornerShape(30),
            onClick = {
                if(water >= 250)
                    water -=250
                viewModel.onEvent(InputScreenUIEvent.OnSetWater(water)) },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.lightpink))
        ) {
            Text(
                text = "-",
                color = colorResource(R.color.pink),
                letterSpacing = 0.1.sp,
                fontSize = 13.sp,
                fontWeight = FontWeight(800),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        //Zobrazenie stavu vypitých pohárov
        Box(modifier = Modifier.height(120.dp), contentAlignment = Alignment.Center)
        {
            Image(painter = painterResource(id = R.drawable.voda), contentDescription = "Voda", modifier = Modifier.fillMaxSize())
            Text(text =(water/250).toString())
        }
        Button(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
                .height(35.dp),
            elevation = null,
            shape = RoundedCornerShape(30),
            onClick = {
                water +=250
                viewModel.onEvent(InputScreenUIEvent.OnSetWater(water)) },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.lightpink))
        ) {
            Text(
                text = "+",
                color = colorResource(R.color.pink),
                letterSpacing = 0.1.sp,
                fontSize = 13.sp,
                fontWeight = FontWeight(800),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}


//Zadavanie teploty
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TemperatureInput( viewModel: InputScreenViewModel = viewModel(), navigateToCalendarScreen: (Int, Int ,Int) -> Unit)
{
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        if (viewModel.uiState.dayToEdit.lifestyle != null &&
            viewModel.uiState.dayToEdit.lifestyle!!.temp != null
        ) {
            Text(text = "Tvoja aktuálna teplota je: " +
                    viewModel.uiState.dayToEdit.lifestyle!!.temp.toString()+ "°C")
        }
        //Zadavanie teploty hodnoty obmedzené na čísla "," sa prepisuje na "."
        var temp by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        OutlinedTextField(
            value = temp,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
            ),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}),
            label = { Text(text = "Zadaj teplotu") },
            onValueChange = {
                temp = it.replace(",",".")
            }
        )

        Button(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
                .height(35.dp),
            elevation = null,
            shape = RoundedCornerShape(30),
            onClick = { viewModel.onEvent(InputScreenUIEvent.OnSetTemp(temp.toFloat())) },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.lightpink))
        ) {
            Text(
                text = "Potvrdit teplotu",
                color = colorResource(R.color.pink),
                letterSpacing = 0.1.sp,
                fontSize = 13.sp,
                fontWeight = FontWeight(800),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

//Zadavanie spánku
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SleepInput( viewModel: InputScreenViewModel = viewModel(), navigateToCalendarScreen: (Int, Int ,Int) -> Unit)
{
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        //Pokial bol už zadaný nejaký spánok vypíše sa
        if (viewModel.uiState.dayToEdit.lifestyle != null &&
            viewModel.uiState.dayToEdit.lifestyle!!.sleep != null
        ) {
            val sleep = viewModel.uiState.dayToEdit.lifestyle!!.sleep
            if (sleep != null) {
                val minutes = sleep%60
                val hours :Int = sleep/60
                Text(text = "Tvoj aktualny spánok je: " +
                        hours.toString()+ " hodín a "+ minutes.toString()+" minút")
            }
        }
        val mContext = LocalContext.current
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val startSleep = remember {mutableStateOf("")}
        val endSleep = remember {mutableStateOf("")}
        //Vyberač času pre zahajenie spánku
        val mTimePickerDialog = TimePickerDialog(
            mContext,
            {_, hour : Int, minute: Int ->
                startSleep.value = "$hour:$minute"
            }, hour, minute, true
        )
        //Ak neni zadana žiadna hodnota je čas nastavený na 0:00
        if(startSleep.value=="")
            startSleep.value="0:00"
        //Vyberač času pre ukončenie spánku
        val mTimePickerDialog2 = TimePickerDialog(
            mContext,
            {_, hour : Int, minute: Int ->
                endSleep.value = "$hour:$minute"
            }, hour, minute, true
        )
        //Ak neni zadana žiadna hodnota je čas nastavený na 0:00
        if(endSleep.value=="")
            endSleep.value="0:00"
        Column(Modifier.height(120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Tačítko pre zobrazenie zadavača času pre započatie spánku
            Button(modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
                .height(35.dp),
                onClick = { mTimePickerDialog.show() },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.lightpink)),
                shape = RoundedCornerShape(30),
                elevation = null,) {
                Text(
                    text = "Zadat začiatok spánku",
                    color = colorResource(R.color.pink),
                    letterSpacing = 0.1.sp,
                    fontSize = 13.sp,
                    fontWeight = FontWeight(800),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Text(text = "Začiatok spánku: ${startSleep.value}", fontSize = 10.sp)
        }

        Column(Modifier.height(120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Tačítko pre zobrazenie zadavača času pre ukončenie spánku
            Button(modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 20.dp)
                .height(35.dp),
                onClick = { mTimePickerDialog2.show() },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.lightpink)),
                shape = RoundedCornerShape(30),
                elevation = null,) {
                Text(
                    text = "Zadat koniec spánku",
                    color = colorResource(R.color.pink),
                    letterSpacing = 0.1.sp,
                    fontSize = 13.sp,
                    fontWeight = FontWeight(800),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Text(text = "Koniec spánku: ${endSleep.value}", fontSize = 10.sp)
        }
        //Potvrdenie zadaného spánku
        Button(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
                .height(35.dp),
            elevation = null,
            shape = RoundedCornerShape(30),
            onClick = { viewModel.onEvent(InputScreenUIEvent.OnSetSleep(startSleep.value,endSleep.value))},
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.lightpink))
        ) {
            Text(
                text = "Potvrdit spánok",
                color = colorResource(R.color.pink),
                letterSpacing = 0.1.sp,
                fontSize = 13.sp,
                fontWeight = FontWeight(800),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}