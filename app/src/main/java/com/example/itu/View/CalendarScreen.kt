package com.example.itu.View

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.itu.R
import com.example.itu.ViewModel.CalendarScreenViewModel
import com.example.itu.ViewModel.CalendarScreenUiState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.itu.Model.*
import com.example.itu.ViewModel.CalendarScreenUIEvent

// Authors: Matej Kocman, Tereza Straková

// Author - marked: Tereza Straková
// Author - rest: Matej Kocman

@Composable
fun CalendarScreen(
    viewModel: CalendarScreenViewModel = viewModel(),
    navigateToInputScreen: (Int, Int, Int) -> Unit,
    navigateToOrderCategoriesScreen: () -> Unit
) {
    LocalLifecycleOwner.current.lifecycle.addObserver(viewModel)

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(viewModel.uiState.selectedDayDate, viewModel.uiState.todayDate, viewModel::onEvent)
        DoW(arrayOf("Ne", "Po", "Ut", "St", "Št", "Pi", "So"))
        CalendarGrid(viewModel.uiState, viewModel::onEvent)
        PinkButtons(
            {
                navigateToInputScreen(
                    viewModel.uiState.selectedDayDate.year,
                    viewModel.uiState.selectedDayDate.month,
                    viewModel.uiState.selectedDayDate.day
                )
            },
            navigateToOrderCategoriesScreen
        )
        DateAndQuestionMark(viewModel.uiState.todayDate, viewModel.uiState.daysInCalendar.first {
            it.date == viewModel.uiState.selectedDayDate
        }.phase ,navigateToOrderCategoriesScreen)
        TrackersGrid(
            modifier = Modifier.weight(1f), dayToDisplay = viewModel.uiState.daysInCalendar.first {
                it.date == viewModel.uiState.selectedDayDate
            }, categoryOrder = viewModel.uiState.categoryOrder,
            categoryColor = viewModel.uiState.categoryColor
        )
        BottomBar({
            navigateToInputScreen(
                viewModel.uiState.selectedDayDate.year,
                viewModel.uiState.selectedDayDate.month,
                viewModel.uiState.selectedDayDate.day
            )
        })
    }
}

@Composable
fun TopBar(
    selectedDayDate: CalendarDate, todayDate: CalendarDate, onEvent: (CalendarScreenUIEvent) -> Unit
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        contentColor = Color.Black,
        elevation = 0.dp,
        contentPadding = PaddingValues(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_hamburger_menu),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .aspectRatio(1f)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val month: String
                when (selectedDayDate.month) {
                    1 -> month = "Január"
                    2 -> month = "Február"
                    3 -> month = "Marec"
                    4 -> month = "Apríl"
                    5 -> month = "Máj"
                    6 -> month = "Jún"
                    7 -> month = "Júl"
                    8 -> month = "August"
                    9 -> month = "September"
                    10 -> month = "Október"
                    11 -> month = "November"
                    12 -> month = "December"
                    else -> {
                        month = "neviem"
                    }
                }
                Icon(
                    painterResource(id = R.drawable.ic_left),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .aspectRatio(1f)
                        .clickable {
                            onEvent(
                                CalendarScreenUIEvent.OnSelectDay(
                                    CalendarDate(
                                        selectedDayDate.year,
                                        selectedDayDate.month - 1,
                                        1
                                    )
                                )
                            )
                        }
                )
                Text(
                    text = month,
                    fontSize = 22.sp,
                    fontWeight = FontWeight(700),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
                Icon(
                    painterResource(id = R.drawable.ic_right),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .aspectRatio(1f)
                        .clickable {
                            onEvent(
                                CalendarScreenUIEvent.OnSelectDay(
                                    CalendarDate(
                                        selectedDayDate.year,
                                        selectedDayDate.month + 1,
                                        1
                                    )
                                )
                            )
                        }
                )
            }
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onEvent(CalendarScreenUIEvent.OnSelectDay(todayDate))
                }
                .alpha(if (todayDate != selectedDayDate) 1f else 0f)) {
                Icon(
                    painterResource(id = R.drawable.ic_calendar),
                    tint = colorResource(id = R.color.lightpurple),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.75f)
                        .aspectRatio(1f)
                )
                Text(
                    text = todayDate.day.toString(),
                    modifier = Modifier.offset(y = 3.dp),
                    color = colorResource(id = R.color.lightpurple),
                    fontSize = 20.sp,
                    fontWeight = FontWeight(1000),
                )
            }
        }
    }
}

@Composable
fun DoW(DoWStrings: Array<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 3.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (day in DoWStrings) {
            Text(
                text = day,
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = colorResource(R.color.gray)
            )
        }
    }
}

@Composable
fun CalendarGrid(uiState: CalendarScreenUiState, onEvent: (CalendarScreenUIEvent) -> Unit) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val dayAspectRatio = remember { mutableStateOf((maxWidth / 7) / (maxHeight * 0.6f / 6)) }
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
        ) {
            itemsIndexed(uiState.daysInCalendar) { index, it ->
                CalendarDay(
                    dayAspectRatio.value,
                    it,
                    uiState.selectedDayDate.month,
                    uiState.todayDate == it.date,
                    uiState.selectedDayDate == it.date,
                    uiState.daysOfCycle[index],
                    uiState.categoryOrder,
                    uiState.categoryColor,
                    onEvent
                )
            }
        }
    }
}

@Composable
fun CalendarDay(
    dayAspectRatio: Float,
    dayToDisplay: Day,
    selectedDayMonth: Int,
    isToday: Boolean,
    isSelectedDay: Boolean,
    dayOfCycle: String,
    categoryOrder: List<Category>,
    categoryColor: Map<Category, Color>,
    onEvent: (CalendarScreenUIEvent) -> Unit
) {
    val icons = arrayListOf<Painter>()
    val colors = arrayListOf<Color>()
    repeat(4) { index ->
        when (categoryOrder[index]) {
            Category.SEX -> {
                colors.add(
                    if (dayToDisplay.sex == null) Color.Transparent
                    else categoryColor[Category.SEX]!!
                )
                icons.add(painterResource(id = R.drawable.ic_heart))
            }
            Category.LIFESTYLE -> {
                colors.add(
                    if (dayToDisplay.lifestyle == null) Color.Transparent
                    else categoryColor[Category.LIFESTYLE]!!
                )
                icons.add(painterResource(id = R.drawable.ic_scale))
            }
            Category.SYMPTOMS -> {
                colors.add(
                    if (dayToDisplay.symptoms == null) Color.Transparent
                    else categoryColor[Category.SYMPTOMS]!!
                )
                icons.add(painterResource(id = R.drawable.ic_report))
            }
            Category.MUCUS -> {
                colors.add(
                    if (dayToDisplay.cervicalMucus == null) Color.Transparent
                    else categoryColor[Category.MUCUS]!!
                )
                icons.add(painterResource(id = R.drawable.ic_panties))
            }
            Category.LOCHIA -> {
                colors.add(
                    if (dayToDisplay.lochia == null) Color.Transparent
                    else categoryColor[Category.LOCHIA]!!
                )
                icons.add(painterResource(id = R.drawable.ic_lochia))
            }
            Category.MOOD -> {
                colors.add(
                    if (dayToDisplay.mood == null) Color.Transparent
                    else categoryColor[Category.MOOD]!!
                )
                icons.add(painterResource(id = R.drawable.ic_mood))
            }
            Category.NOTE -> {
                colors.add(
                    if (dayToDisplay.note == null) Color.Transparent
                    else categoryColor[Category.NOTE]!!
                )
                icons.add(painterResource(id = R.drawable.ic_note))
            }
            Category.BREASTS -> {
                colors.add(
                    if (dayToDisplay.breastExam == null) Color.Transparent
                    else categoryColor[Category.BREASTS]!!
                )
                icons.add(painterResource(id = R.drawable.ic_bra))
            }
            Category.TESTS -> {
                colors.add(
                    if (dayToDisplay.ovulationTest == null &&
                        dayToDisplay.fertilityTest == null &&
                        dayToDisplay.gravidityTest == null
                    )
                        Color.Transparent
                    else categoryColor[Category.TESTS]!!
                )
                icons.add(painterResource(id = R.drawable.ic_test))
            }
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .aspectRatio(dayAspectRatio)
            .border(width = 1.dp, color = colorResource(id = R.color.lineblue))
            .alpha(if (dayToDisplay.date.month == selectedDayMonth) 1f else 0.2f)
            .clickable(onClick = { onEvent(CalendarScreenUIEvent.OnSelectDay(dayToDisplay.date)) })
    ) {
        if (isSelectedDay) {
            DayHighlighterBox(color = colorResource(id = R.color.purple_700))
        } else if (isToday) {
            DayHighlighterBox(color = colorResource(id = R.color.lightpurple))
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.15f)
                    .background(
                        color = when (dayToDisplay.phase) {
                            Phase.LIGHT_FLOW -> colorResource(id = R.color.pink)
                            Phase.MEDIUM_FLOW -> colorResource(id = R.color.red)
                            Phase.HEAVY_FLOW -> colorResource(id = R.color.darkred)
                            Phase.DISASTER_FLOW -> colorResource(id = R.color.disasterred)
                            Phase.FERTILE -> colorResource(id = R.color.green)
                            Phase.OVULATING -> colorResource(id = R.color.darkgreen)
                            else -> Color.Transparent
                        }
                    )
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.35f)
            ) {
                Text(
                    text = dayToDisplay.date.day.toString(),
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700)
                )
                Text(
                    modifier = Modifier.offset(x = 19.dp, y = 2.dp),
                    text = dayOfCycle,
                    color = colorResource(id = R.color.gray),
                    fontSize = 12.sp
                )

            }
            Spacer(modifier = Modifier.weight(0.05f))
            Row(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxWidth(0.65f),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    painter = icons[0],
                    contentDescription = null,
                    tint = colors[0]
                )
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    painter = icons[1],
                    contentDescription = null,
                    tint = colors[1]
                )
            }
            Spacer(modifier = Modifier.weight(0.05f))
            Row(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxWidth(0.65f),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    painter = icons[2],
                    contentDescription = null,
                    tint = colors[2]
                )
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    painter = icons[3],
                    contentDescription = null,
                    tint = colors[3]
                )
            }
            Spacer(modifier = Modifier.weight(0.05f))
        }
    }
}

@Composable
fun DayHighlighterBox(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 4.dp, color = color, shape = RoundedCornerShape(15.dp)
            )
            .zIndex(10f)
    )
}

// Author: Tereza Straková
@Composable
fun PinkButtons(
    navigateToInputScreen: () -> Unit,
    navigateToOrderCategoriesScreen: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        PinkButton(
            "UPRAVIŤ MENŠTRUÁCIU", modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            navigateToOrderCategoriesScreen()
        }
        PinkButton("DAJ POZNÁMKU", modifier = Modifier.fillMaxWidth()) {
            navigateToInputScreen()
        }
    }
}

@Composable
fun PinkButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .height(35.dp),
        elevation = null,
        shape = RoundedCornerShape(30),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.lightpink))
    )

    {
        Text(
            text = text,
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
// End Author: Tereza Straková

@Composable
fun DateAndQuestionMark(todayDate: CalendarDate, selectedDayPhase: Phase?, navigateToOrderCategoriesScreen: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = todayDate.day.toString() + "." +
                        todayDate.month.toString() + "." +
                        todayDate.year.toString() + "  ",
                fontSize = 18.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.End
            )
            Text(
                modifier = Modifier
                    .padding(start = 0.dp)
                    .offset(y = 1.dp),
                text = if(selectedDayPhase == null) "" else "-  " + PhaseDict[selectedDayPhase]!!,
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = Color.DarkGray
            )
        }
        Box(
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.pink),
                    shape = CircleShape
                )
                .size(20.dp)
                .clickable(onClick = navigateToOrderCategoriesScreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_question_mark),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.fillMaxSize(0.8f)
            )
        }
    }
}

@Composable
fun TrackersGrid(
    modifier: Modifier,
    dayToDisplay: Day,
    categoryOrder: List<Category>,
    categoryColor: Map<Category, Color>
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    ) {
        items(categoryOrder) { category ->
            when (category) {
                Category.SEX -> Tracker(
                    R.drawable.ic_heart,
                    "INTIMITA",
                    { IntimityTrackerText(dayToDisplay.sex!!) },
                    categoryColor[Category.SEX]!!,
                    dayToDisplay.sex
                )
                Category.LIFESTYLE -> Tracker(
                    R.drawable.ic_scale,
                    "ŽIVOTNÝ ŠTÝL",
                    { LifestyleTrackerText(dayToDisplay.lifestyle!!) },
                    categoryColor[Category.LIFESTYLE]!!,
                    dayToDisplay.lifestyle
                )
                Category.SYMPTOMS -> Tracker(
                    R.drawable.ic_report,
                    "SYMPTÓMY",
                    { SetTrackerText(dayToDisplay.symptoms!!, SymptomsDict as Map<Any, String>) },
                    categoryColor[Category.SYMPTOMS]!!,
                    dayToDisplay.symptoms
                )
                Category.MUCUS -> Tracker(
                    R.drawable.ic_panties,
                    "CERVIX. HLIEN",
                    { SetTrackerText(dayToDisplay.cervicalMucus!!, MucusDict as Map<Any, String>) },
                    categoryColor[Category.MUCUS]!!,
                    dayToDisplay.cervicalMucus
                )
                Category.LOCHIA -> Tracker(
                    R.drawable.ic_lochia,
                    "LOCHIA",
                    { LochiaTrackerText(dayToDisplay.lochia!!) },
                    categoryColor[Category.LOCHIA]!!,
                    dayToDisplay.lochia
                )
                Category.MOOD -> Tracker(
                    R.drawable.ic_mood,
                    "NÁLADA",
                    { SetTrackerText(dayToDisplay.mood!!, MoodDict as Map<Any, String>) },
                    categoryColor[Category.MOOD]!!,
                    dayToDisplay.mood
                )
                Category.NOTE -> Tracker(
                    R.drawable.ic_note,
                    "POZNÁMKY",
                    { NoteTrackerText(dayToDisplay.note!!) },
                    categoryColor[Category.NOTE]!!,
                    dayToDisplay.note
                )
                Category.BREASTS -> Tracker(
                    R.drawable.ic_bra,
                    "PRSNÍKY",
                    { SetTrackerText(dayToDisplay.breastExam!!, BreastDict as Map<Any, String>) },
                    categoryColor[Category.BREASTS]!!,
                    dayToDisplay.breastExam
                )
                Category.TESTS -> {
                    var list: List<Any>? = listOfNotNull(
                        dayToDisplay.ovulationTest,
                        dayToDisplay.gravidityTest,
                        dayToDisplay.fertilityTest
                    )
                    if (list.isNullOrEmpty()) list = null
                    Tracker(R.drawable.ic_test, "TESTY", {
                        TestTrackerText(
                            dayToDisplay.ovulationTest,
                            dayToDisplay.gravidityTest,
                            dayToDisplay.fertilityTest
                        )
                    }, categoryColor[Category.TESTS]!!, list)
                }
            }
        }
    }
}


// Author: Tereza Straková
@Composable
fun BottomBar(navigateToInputScreen: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BottomBarButton(
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            icon = painterResource(id = R.drawable.ic_flower),
            title = "Dnes"
        )
        BottomBarButton(
            modifier = Modifier.weight(1f),
            color = colorResource(id = R.color.pink),
            icon = painterResource(id = R.drawable.ic_calendar_filled),
            title = "Kalendár"
        )
        BottomBarAddSymptom(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = navigateToInputScreen)
        )
        BottomBarButton(
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            icon = painterResource(id = R.drawable.ic_notification),
            title = "Pripomenutia"
        )
        BottomBarButton(
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            icon = painterResource(id = R.drawable.ic_graph),
            title = "Prehľad"
        )
    }
}

@Composable
fun BottomBarButton(modifier: Modifier, color: Color, icon: Painter, title: String) {
    Column(
        modifier = modifier.padding(bottom = 6.dp, top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(22.dp)
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight(700),
            color = color
        )
    }
}

@Composable
fun BottomBarAddSymptom(modifier: Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Icon(
            painter = painterResource(id = R.drawable.ic_hexagon),
            contentDescription = null,
            tint = colorResource(id = R.color.pink),
            modifier = Modifier.size(50.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
    }
}
// End Author: Tereza Straková