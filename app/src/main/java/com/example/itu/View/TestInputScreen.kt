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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itu.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.example.itu.Model.*
import com.example.itu.ViewModel.*

// Author: Jakub Drobena


@Composable
fun Ovulacia(name : String, painter : Painter , modifier: Modifier = Modifier,day: TesInputScreenViewModel, positive :Boolean)
{
    val toggled : Boolean?
    if(positive)
        toggled = day.uiState.dayToEdit.ovulationTest == true
    else
        toggled = day.uiState.dayToEdit.ovulationTest == false
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    if(positive)
                        day.onEvent(TestInputScreenUIEvent.OnAddOvulation)
                    else
                        day.onEvent(TestInputScreenUIEvent.OnAddOvulationNeg)
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
fun Fertility(name : Fertility, painter : Painter , modifier: Modifier = Modifier,day: TesInputScreenViewModel)
{
    val toggled :Boolean?
    toggled = day.uiState.dayToEdit.fertilityTest == name
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    when(name)
                    {
                        Fertility.HIGHEST -> day.onEvent(TestInputScreenUIEvent.OnFertilityHighest)
                        Fertility.HIGH -> day.onEvent(TestInputScreenUIEvent.OnFertilityHigh)
                        Fertility.LOW -> day.onEvent(TestInputScreenUIEvent.OnFertilityLow)
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
                    FertilityDict[name]?.let { Text(text = it) }
                }

            }
        }

    }
}
@Composable
fun Gravidita(name : Gravidity, painter : Painter , modifier: Modifier = Modifier,day: TesInputScreenViewModel)
{
    val toggled :Boolean?
    toggled = day.uiState.dayToEdit.gravidityTest == name
    Box(modifier = Modifier.fillMaxSize()){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    when(name)
                    {
                        Gravidity.NEGATIVE -> day.onEvent(TestInputScreenUIEvent.OnGravidityNeg)
                        Gravidity.FEINT_LINE -> day.onEvent(TestInputScreenUIEvent.OnGravidityFaint)
                        Gravidity.POSITIVE -> day.onEvent(TestInputScreenUIEvent.OnGravidityPos)
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
                    GravidityDict[name]?.let { Text(text = it) }
                }

            }
        }

    }
}

//View pre zadavanie jednotlivých testov
@Composable
fun TestInputScreen(viewModel: TesInputScreenViewModel = viewModel(), navigateToCalendarScreen: (Int, Int ,Int) -> Unit)
{

    Column() {
        //Tlačítko pre navrat na predchádzajúcu obrazovku
        Box(
            modifier = Modifier.height(40.dp),
            contentAlignment = Alignment.TopStart
        )
        {
            Image(painter = painterResource(id = R.drawable.back), contentDescription = "Back Button", modifier = Modifier.clickable { navigateToCalendarScreen(viewModel.uiState.dayToEdit.date.year,viewModel.uiState.dayToEdit.date.month,viewModel.uiState.dayToEdit.date.day) })
        }
        Divider(color = Color.Gray, thickness = 1.dp)
        Text(modifier = Modifier.height(30.dp), text = "Ovulácia", fontSize = 23.sp)
        //Možnosti ovulácie
        LazyRow(modifier = Modifier.height(131.dp))
        {
            item{
                Ovulacia(name = "Ovulácia Pozitiv", painter = painterResource(id = R.drawable.egg),day = viewModel, positive = true)
            }
            item{
                Ovulacia(name = "Ovulácia Negatív", painter = painterResource(id = R.drawable.noegg),day = viewModel, positive = false)
            }
        }
        Divider(color = Color.Gray, thickness = 1.dp)
        Text(modifier = Modifier.height(30.dp), text = "Gravidita", fontSize = 23.sp)
        //Možnosti gravidity
        LazyRow(modifier = Modifier.height(131.dp))
        {
            item{
                Gravidita(name = Gravidity.POSITIVE, painter = painterResource(id = R.drawable.pregnant),day = viewModel)
            }
            item{
                Gravidita(name = Gravidity.NEGATIVE, painter = painterResource(id = R.drawable.negative),day = viewModel)
            }
            item{
                Gravidita(name = Gravidity.FEINT_LINE, painter = painterResource(id = R.drawable.idk),day = viewModel)
            }
        }
        Divider(color = Color.Gray, thickness = 1.dp)
        Text(modifier = Modifier.height(30.dp), text = "Plodnosť", fontSize = 23.sp)
        //Možnosti plodnosti
        LazyRow(modifier = Modifier.height(131.dp))
        {
            item{
                Fertility(name = Fertility.HIGHEST, painter = painterResource(id = R.drawable.highfert),day = viewModel)
            }
            item{
                Fertility(name = Fertility.HIGH, painter = painterResource(id = R.drawable.fertility),day = viewModel)
            }
            item{
                Fertility(name = Fertility.LOW, painter = painterResource(id = R.drawable.lowfert),day = viewModel)
            }
        }
    }
}