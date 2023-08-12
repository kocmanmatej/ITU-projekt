package com.example.itu.View

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itu.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.itu.Model.*
import com.example.itu.ViewModel.*
import java.util.ArrayList

// Author: Jakub Drobena

@Composable
fun DayDetail(viewModel: DayDetailViewModel = viewModel(), navigateToInputScreen: (Int, Int, Int, String) -> Unit, navigateToCalendarScreen: () -> Unit, navigateToSexInputScreen: (Int, Int, Int, Int)->Unit,navigateToTestInputScreen: (Int, Int, Int)->Unit)
{
    Column(modifier = Modifier.fillMaxHeight()) {
        Box(
            modifier = Modifier.height(40.dp),
            contentAlignment = Alignment.TopStart
        )
        {
            Image(painter = painterResource(id = R.drawable.back), contentDescription = "Back Button", modifier = Modifier.clickable { navigateToCalendarScreen() })
        }
        Symptoms(Modifier,viewModel, navigateToInputScreen, navigateToSexInputScreen,navigateToTestInputScreen)
    }

}
@Composable
fun SexList(day: DayDetailViewModel, navigateToSexInputScreen: (Int, Int, Int, Int) -> Unit)
{
    var items: ArrayList<Sex>? = day.uiState.dayToEdit.sex
    LazyRow(modifier = Modifier.fillMaxSize())
    {
        item{
            SexIconAdd(painterResource(id = R.drawable.plus),day, navigateToSexInputScreen)
        }
        if (items != null) {
            items(items.size)
            {
                i->
                SexIcon(painterResource(id = R.drawable.sex),Modifier.fillMaxSize(),day,  i, navigateToSexInputScreen)
            }
        }
    }
}

@Composable
fun SexIconAdd(painter: Painter,day: DayDetailViewModel,navigateToSexInputScreen: (Int, Int, Int, Int) -> Unit)
{
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    day.onEvent(DayDetailUiEvent.OnAddSex)
                    val position = day.uiState.dayToEdit.sex!!.size - 1
                    navigateToSexInputScreen(
                        day.uiState.dayToEdit.date.year,
                        day.uiState.dayToEdit.date.month,
                        day.uiState.dayToEdit.date.day,
                        position
                    )
                }
                .padding(1.dp),
            shape = RoundedCornerShape(100.dp),
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

            }
        }

    }
}
@Composable
fun SexIcon(painter: Painter,modifier: Modifier = Modifier,day: DayDetailViewModel, position :Int, navigateToSexInputScreen: (Int, Int, Int, Int) -> Unit)
{

    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    navigateToSexInputScreen(
                        day.uiState.dayToEdit.date.year,
                        day.uiState.dayToEdit.date.month,
                        day.uiState.dayToEdit.date.day,
                        position
                    )
                }
                .padding(1.dp),
            shape = RoundedCornerShape(100.dp),
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

            }
        }

    }
}
@Composable
fun LStyle(name : String, painter : Painter , modifier: Modifier = Modifier,selectedDayDate: CalendarDate,navigateToInputScreen: (Int, Int, Int, String) -> Unit)
{
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    navigateToInputScreen(
                        selectedDayDate.year,
                        selectedDayDate.month,
                        selectedDayDate.day,
                        name
                    )
                }
                .padding(1.dp),
            shape = RoundedCornerShape(100.dp),
            backgroundColor = Color.Cyan,
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

@Composable
fun Symptom(name : Symptom, painter : Painter, modifier: Modifier = Modifier, day: DayDetailViewModel)
{
    val toggled : Boolean = day.uiState.dayToEdit.symptoms?.contains(name) == true
    Box(modifier = Modifier.fillMaxSize()){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                when (name) {
                    Symptom.ACNE -> day.onEvent(DayDetailUiEvent.OnAddAcne)
                    Symptom.BELLY_CRAMPS -> day.onEvent(DayDetailUiEvent.OnAddBellyCramps)
                    Symptom.BLOATING -> day.onEvent(DayDetailUiEvent.OnAddBloating)
                    Symptom.BREAST_PAIN -> day.onEvent(DayDetailUiEvent.OnAddBreastPain)
                    Symptom.INSOMNIA -> day.onEvent(DayDetailUiEvent.OnAddInsomnia)
                    Symptom.CONGESTION -> day.onEvent(DayDetailUiEvent.OnAddCongestion)
                    Symptom.TIREDNESS -> day.onEvent(DayDetailUiEvent.OnAddTiredness)
                    Symptom.GASSINESS -> day.onEvent(DayDetailUiEvent.OnAddGassiness)
                    Symptom.HEADACHE -> day.onEvent(DayDetailUiEvent.OnAddHeadAche)
                    Symptom.LOWER_BACK_PAIN -> day.onEvent(DayDetailUiEvent.OnAddLowerBackPain)
                    Symptom.DIARRHEA -> day.onEvent(DayDetailUiEvent.OnAddDiarrhea)
                    Symptom.SPOTTING -> day.onEvent(DayDetailUiEvent.OnAddSpotting)
                    Symptom.SWEARING -> day.onEvent(DayDetailUiEvent.OnAddSwearing)
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
                SymptomsDict[name]?.let { Text(text = it) }
            }

        }
    }

    }
}
@Composable
fun Day(modifier: Modifier= Modifier,day: String,date: String)
{
    Box(modifier = Modifier
        .fillMaxHeight()
        .border(BorderStroke(1.dp, Color.Black)))
    {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(1f), contentAlignment = Alignment.TopCenter)
            {
                Text(text = day, fontSize = 20.sp)
            }
            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(1f), contentAlignment = Alignment.Center)
            {
                Text(text = date,fontSize = 20.sp)
            }
            
        }
    }
}
@Composable
fun Mood(name : Mood, painter : Painter , modifier: Modifier = Modifier,day: DayDetailViewModel)
{
    val toggled : Boolean = day.uiState.dayToEdit.mood?.contains(name) == true
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    when (name) {
                        Mood.NORMAL -> day.onEvent(DayDetailUiEvent.OnAddNormal)
                        Mood.ANGRY -> day.onEvent(DayDetailUiEvent.OnAddAngry)
                        Mood.ANGSTY -> day.onEvent(DayDetailUiEvent.OnAddAngsty)
                        Mood.DEPRESSED -> day.onEvent(DayDetailUiEvent.OnAddDepressed)
                        Mood.IN_LOVE -> day.onEvent(DayDetailUiEvent.OnAddInLove)
                        Mood.SAD -> day.onEvent(DayDetailUiEvent.OnAddSad)
                        Mood.TIRED -> day.onEvent(DayDetailUiEvent.OnAddTired)
                        Mood.EMOTIONAL -> day.onEvent(DayDetailUiEvent.OnAddEmotional)
                        else -> {}
                    }
                }
                .padding(1.dp),
            shape = RoundedCornerShape(100.dp),
            backgroundColor = if(toggled) Color.Yellow else Color.Gray,
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
                    MoodDict[name]?.let { Text(text = it) }
                }

            }
        }

    }
}
@Composable
fun Breasts(name : Breasts, painter : Painter , modifier: Modifier = Modifier,day: DayDetailViewModel)
{
    val toggled : Boolean = day.uiState.dayToEdit.breastExam?.contains(name) == true
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    when (name) {
                        Breasts.DIMPLE -> day.onEvent(DayDetailUiEvent.OnAddDimple)
                        Breasts.SWOLLEN -> day.onEvent(DayDetailUiEvent.OnAddSwolen)
                        Breasts.FISSURES -> day.onEvent(DayDetailUiEvent.OnAddFissures)
                        Breasts.DISCHARGE -> day.onEvent(DayDetailUiEvent.OnAddDischarge)
                        Breasts.KNOT -> day.onEvent(DayDetailUiEvent.OnAddKnot)
                        Breasts.PAIN -> day.onEvent(DayDetailUiEvent.OnAddPain)
                        else -> {}
                    }
                }
                .padding(1.dp),
            shape = RoundedCornerShape(100.dp),
            backgroundColor = if(toggled) Color.Yellow else Color.Gray,
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
                    BreastDict[name]?.let { Text(text = it) }
                }

            }
        }

    }
}

@Composable
fun Mucus(name : Mucus, painter : Painter , modifier: Modifier = Modifier,day: DayDetailViewModel)
{
    val toggled : Boolean = day.uiState.dayToEdit.cervicalMucus?.contains(name) == true
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    when (name) {
                        Mucus.CREAMY -> day.onEvent(DayDetailUiEvent.OnAddCreamy)
                        Mucus.DRY -> day.onEvent(DayDetailUiEvent.OnAddDry)
                        Mucus.EGGWHITE -> day.onEvent(DayDetailUiEvent.OnAddEggWhite)
                        Mucus.STICKY -> day.onEvent(DayDetailUiEvent.OnAddSticky)
                        Mucus.WATERY -> day.onEvent(DayDetailUiEvent.OnAddWatery)
                        Mucus.UNUSUAL -> day.onEvent(DayDetailUiEvent.OnAddUnusual)
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
                    MucusDict[name]?.let { Text(text = it) }
                }

            }
        }

    }
}
@Composable
fun Test(name : String, painter : Painter ,day: DayDetailViewModel ,navigateToTestInputScreen: (Int, Int, Int) -> Unit)
{
    val toggled = day.uiState.dayToEdit.ovulationTest !=null || day.uiState.dayToEdit.fertilityTest !=null || day.uiState.dayToEdit.gravidityTest !=null
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateToTestInputScreen(day.uiState.dayToEdit.date.year,day.uiState.dayToEdit.date.month,day.uiState.dayToEdit.date.day)
                }
                .padding(1.dp),
            shape = RoundedCornerShape(100.dp),
            backgroundColor = if(toggled) Color.Yellow else Color.Gray,
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
@Composable
fun Lochia(name : Lochia, painter : Painter , modifier: Modifier = Modifier,day: DayDetailViewModel)
{
    val toggled = day.uiState.dayToEdit.lochia == name
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    when (name) {
                        Lochia.NONE -> day.onEvent(DayDetailUiEvent.OnAddNone)
                        Lochia.PINK -> day.onEvent(DayDetailUiEvent.OnAddPink)
                        Lochia.RED -> day.onEvent(DayDetailUiEvent.OnAddRed)
                        Lochia.YELLOW -> day.onEvent(DayDetailUiEvent.OnAddYellow)
                        else -> {}
                    }
                }
                .padding(1.dp),
            shape = RoundedCornerShape(100.dp),
            backgroundColor = if(toggled) Color.Yellow else Color.Gray,
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
                    LochiaDict[name]?.let { Text(text = it) }
                }

            }
        }

    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Symptoms(modifier: Modifier = Modifier, viewModel: DayDetailViewModel, navigateToInputScreen: (Int, Int, Int, String) -> Unit,navigateToSexInputScreen: (Int, Int, Int, Int) -> Unit, navigateToTestInputScreen: (Int, Int, Int) -> Unit) {
    Column(modifier = Modifier
        .fillMaxHeight()
        .verticalScroll(rememberScrollState())) {
        //DayPicker()
        Text(modifier = Modifier.height(30.dp), text = "Sex", fontSize = 23.sp)
        SexList(day = viewModel, navigateToSexInputScreen)
        Divider(color = Color.Gray, thickness = 1.dp)
        Text(modifier = Modifier.height(30.dp), text = "Životný Štýl", fontSize = 23.sp)
        LazyRow(modifier = Modifier.height(131.dp))
        {
            item{
                LStyle(name = "Spánok", painter = painterResource(id = R.drawable.spanie), selectedDayDate = viewModel.uiState.dayToEdit.date, modifier = Modifier,
                    navigateToInputScreen =navigateToInputScreen)
            }
            item{
                LStyle(name = "Teplota", painter = painterResource(id = R.drawable.teplota), selectedDayDate = viewModel.uiState.dayToEdit.date, modifier = Modifier,
                    navigateToInputScreen =navigateToInputScreen)
            }
            item{
                LStyle(name = "Voda", painter = painterResource(id = R.drawable.voda), selectedDayDate = viewModel.uiState.dayToEdit.date, modifier = Modifier,
                    navigateToInputScreen =navigateToInputScreen)
            }
            item{
                LStyle(name = "Váha", painter = painterResource(id = R.drawable.vava), selectedDayDate = viewModel.uiState.dayToEdit.date, modifier = Modifier,
                    navigateToInputScreen =navigateToInputScreen
                )
            }
        }
        Divider(color = Color.Gray, thickness = 1.dp)
        Text(modifier = Modifier.height(30.dp), text = "Symptómy", fontSize = 23.sp)
        LazyRow(modifier = Modifier.height(180.dp))
        {
            item{
                Symptom(name = Symptom.BELLY_CRAMPS, painter = painterResource(id = R.drawable.brucho), day = viewModel)
            }
            item{
                Symptom(name = Symptom.ACNE, painter = painterResource(id = R.drawable.akne), day = viewModel)
            }
            item{
                Symptom(name = Symptom.LOWER_BACK_PAIN, painter = painterResource(id = R.drawable.chrbat), day = viewModel)
            }
            item{
                Symptom(name = Symptom.SWEARING, painter = painterResource(id = R.drawable.swear), day = viewModel)
            }
            item{
                Symptom(name = Symptom.HEADACHE, painter = painterResource(id = R.drawable.hlava), day = viewModel)
            }
            item{
                Symptom(name = Symptom.DIARRHEA, painter = painterResource(id = R.drawable.hnacka), day = viewModel)
            }
            item{
                Symptom(name = Symptom.SPOTTING, painter = painterResource(id = R.drawable.krv), day = viewModel)
            }
            item{
                Symptom(name = Symptom.BLOATING, painter = painterResource(id = R.drawable.naduvanie), day = viewModel)
            }
            item{
                Symptom(name = Symptom.INSOMNIA, painter = painterResource(id = R.drawable.nespanok), day = viewModel)
            }
            item{
                Symptom(name = Symptom.GASSINESS, painter = painterResource(id = R.drawable.prd), day = viewModel)
            }
            item{
                Symptom(name = Symptom.BREAST_PAIN, painter = painterResource(id = R.drawable.prsniky), day = viewModel)
            }
            item{
                Symptom(name = Symptom.TIREDNESS, painter = painterResource(id = R.drawable.spanok), day = viewModel)
            }
            item{
                Symptom(name = Symptom.CONGESTION, painter = painterResource(id = R.drawable.zapcha), day = viewModel)
            }
        }
        Divider(color = Color.Gray, thickness = 1.dp)
        Text(modifier = Modifier.height(30.dp), text = "Nálada", fontSize = 23.sp)
        LazyRow(modifier = Modifier.height(180.dp))
        {
            item{
                Mood(name = Mood.NORMAL, painter = painterResource(id = R.drawable.normal), day = viewModel)
            }
            item{
                Mood(name = Mood.SAD, painter = painterResource(id = R.drawable.crying), day = viewModel)
            }
            item{
                Mood(name = Mood.DEPRESSED, painter = painterResource(id = R.drawable.depressed), day = viewModel)
            }
            item{
                Mood(name = Mood.EMOTIONAL, painter = painterResource(id = R.drawable.emotional), day = viewModel)
            }
            item{
                Mood(name = Mood.TIRED, painter = painterResource(id = R.drawable.tired), day = viewModel)
            }
            item{
                Mood(name = Mood.IN_LOVE, painter = painterResource(id = R.drawable.love), day = viewModel)
            }
            item{
                Mood(name = Mood.ANGSTY, painter = painterResource(id = R.drawable.anxiety), day = viewModel)
            }
            item{
                Mood(name = Mood.ANGRY, painter = painterResource(id = R.drawable.angry), day = viewModel)
            }

        }
        Divider(color = Color.Gray, thickness = 1.dp)
        Text(modifier = Modifier.height(30.dp), text = "Samovyšetrenie prsníkov", fontSize = 23.sp)
        LazyRow(modifier = Modifier.height(180.dp))
        {
            item{
                Breasts(name = Breasts.PAIN, painter = painterResource(id = R.drawable.pain), day = viewModel)
            }
            item{
                Breasts(name = Breasts.KNOT, painter = painterResource(id = R.drawable.knot), day = viewModel)
            }
            item{
                Breasts(name = Breasts.SWOLLEN, painter = painterResource(id = R.drawable.swallen), day = viewModel)
            }
            item{
                Breasts(name = Breasts.DISCHARGE, painter = painterResource(id = R.drawable.drop), day = viewModel)
            }
            item{
                Breasts(name = Breasts.FISSURES, painter = painterResource(id = R.drawable.crack), day = viewModel)
            }
            item{
                Breasts(name = Breasts.DIMPLE, painter = painterResource(id = R.drawable.hole), day = viewModel)
            }

        }
        Divider(color = Color.Gray, thickness = 1.dp)
        Text(modifier = Modifier.height(30.dp), text = "Poznámky", fontSize = 23.sp)
        var notes by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        Box(modifier = Modifier
            .height(180.dp)
            .padding(10.dp),contentAlignment = Alignment.TopCenter)
        {
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                value = notes,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.onEvent(DayDetailUiEvent.OnAddNote(notes))
                        keyboardController?.hide()
                    }),
                label = { Text(text = "Poznámky") },
                onValueChange = {
                    notes = it
                }
            )
        }
        Divider(color = Color.Gray, thickness = 1.dp)
        Text(modifier = Modifier.height(30.dp), text = "Cervikálny hlien", fontSize = 23.sp)
        LazyRow(modifier = Modifier.height(180.dp))
        {
            item{
                Mucus(name = Mucus.WATERY, painter = painterResource(id = R.drawable.drop), day = viewModel)
            }
            item{
                Mucus(name = Mucus.CREAMY, painter = painterResource(id = R.drawable.cream), day = viewModel)
            }
            item{
                Mucus(name = Mucus.STICKY, painter = painterResource(id = R.drawable.glue), day = viewModel)
            }
            item{
                Mucus(name = Mucus.DRY, painter = painterResource(id = R.drawable.dry), day = viewModel)
            }
            item{
                Mucus(name = Mucus.EGGWHITE, painter = painterResource(id = R.drawable.egg), day = viewModel)
            }
            item{
                Mucus(name = Mucus.UNUSUAL, painter = painterResource(id = R.drawable.microbes), day = viewModel)
            }

        }
        Divider(color = Color.Gray, thickness = 1.dp)
        Text(modifier = Modifier.height(30.dp), text = "Lochia", fontSize = 23.sp)
        LazyRow(modifier = Modifier.height(180.dp))
        {
            item{
                Lochia(name = Lochia.PINK, painter = painterResource(id = R.drawable.pink), day = viewModel)
            }
            item{
                Lochia(name = Lochia.RED, painter = painterResource(id = R.drawable.readness), day = viewModel)
            }
            item{
                Lochia(name = Lochia.YELLOW, painter = painterResource(id = R.drawable.yellow), day = viewModel)
            }
            item{
                Lochia(name = Lochia.NONE, painter = painterResource(id = R.drawable.none), day = viewModel)
            }
        }
        Divider(color = Color.Gray, thickness = 1.dp)
        Text(modifier = Modifier.height(30.dp), text = "Testy a kontrola", fontSize = 23.sp)
        LazyRow(modifier = Modifier.height(180.dp))
        {
            item{
                Test("Testy",painterResource(id = R.drawable.test),viewModel,navigateToTestInputScreen)
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(viewModel: DayDetailViewModel = viewModel())
{

    }

