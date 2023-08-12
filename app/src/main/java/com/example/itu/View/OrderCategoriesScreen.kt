package com.example.itu.View

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.itu.Model.*
import com.example.itu.R
import com.example.itu.ViewModel.OrderCategoriesScreenUIEvent
import com.example.itu.ViewModel.OrderCategoriesViewModel
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import org.burnoutcrew.reorderable.*

// Author: Tereza Straková

@Composable
fun OrderCategoriesScreen(
    viewModel: OrderCategoriesViewModel = viewModel(), navigateToCalendarScreen: () -> Unit
) {
    LocalLifecycleOwner.current.lifecycle.addObserver(viewModel)

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        TopBar(navigateToCalendarScreen)
        Text(
            text = "Poradie kategórií v kalendári zmeníte tak, že kategóriu pridržíte a presuniete.\n" + "Ikony prvých štyroch kategórií sa zobrazia v kalendári.",
            fontSize = 18.sp,
            fontWeight = FontWeight(400),
            modifier = Modifier
                .padding(bottom = 30.dp, top = 10.dp)
                .padding(horizontal = 20.dp)
        )

        Divider(color = Color.LightGray, thickness = 2.dp)

        val reorderState = rememberReorderableLazyListState(onMove = { from, to ->
            viewModel.onEvent(
                OrderCategoriesScreenUIEvent.OnCategoriesReordered(from, to)
            )
        })

        LazyColumn(
            state = reorderState.listState, modifier = Modifier.reorderable(reorderState)
        ) {
            itemsIndexed(viewModel.uiState.categoryOrder, { _, it -> it }) { index, category ->
                ReorderableItem(reorderState, key = category) { isDragging ->
                    val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                    when (category) {
                        Category.SEX -> Category(
                            modifier = Modifier
                                .detectReorderAfterLongPress(reorderState)
                                .shadow(elevation.value)
                                .background(MaterialTheme.colors.surface),
                            iconID = R.drawable.ic_heart,
                            title = "Intimita",
                            primaryColor = viewModel.uiState.categoryColor[Category.SEX]!!
                        ) {
                            viewModel.onEvent(
                                OrderCategoriesScreenUIEvent.OnColorChangeWindowOpen(
                                    viewModel.uiState.categoryColor[Category.SEX]!!,
                                    Category.SEX
                                )
                            )
                        }
                        Category.LIFESTYLE -> Category(
                            modifier = Modifier
                                .detectReorderAfterLongPress(reorderState)
                                .shadow(elevation.value)
                                .background(MaterialTheme.colors.surface),
                            iconID = R.drawable.ic_scale,
                            title = "Životný štýl",
                            primaryColor = viewModel.uiState.categoryColor[Category.LIFESTYLE]!!
                        ) {
                            viewModel.onEvent(
                                OrderCategoriesScreenUIEvent.OnColorChangeWindowOpen(
                                    viewModel.uiState.categoryColor[Category.LIFESTYLE]!!,
                                    Category.LIFESTYLE
                                )
                            )
                        }
                        Category.SYMPTOMS -> Category(
                            modifier = Modifier
                                .detectReorderAfterLongPress(reorderState)
                                .shadow(elevation.value)
                                .background(MaterialTheme.colors.surface),
                            iconID = R.drawable.ic_report,
                            title = "Symptómy",
                            primaryColor = viewModel.uiState.categoryColor[Category.SYMPTOMS]!!
                        ) {
                            viewModel.onEvent(
                                OrderCategoriesScreenUIEvent.OnColorChangeWindowOpen(
                                    viewModel.uiState.categoryColor[Category.SYMPTOMS]!!,
                                    Category.SYMPTOMS
                                )
                            )
                        }
                        Category.MUCUS -> Category(
                            modifier = Modifier
                                .detectReorderAfterLongPress(reorderState)
                                .shadow(elevation.value)
                                .background(MaterialTheme.colors.surface),
                            iconID = R.drawable.ic_panties,
                            title = "Cervikálny hlien",
                            primaryColor = viewModel.uiState.categoryColor[Category.MUCUS]!!
                        ) {
                            viewModel.onEvent(
                                OrderCategoriesScreenUIEvent.OnColorChangeWindowOpen(
                                    viewModel.uiState.categoryColor[Category.MUCUS]!!,
                                    Category.MUCUS
                                )
                            )
                        }
                        Category.LOCHIA -> Category(
                            modifier = Modifier
                                .detectReorderAfterLongPress(reorderState)
                                .shadow(elevation.value)
                                .background(MaterialTheme.colors.surface),
                            iconID = R.drawable.ic_lochia,
                            title = "Lochia",
                            primaryColor = viewModel.uiState.categoryColor[Category.LOCHIA]!!
                        ) {
                            viewModel.onEvent(
                                OrderCategoriesScreenUIEvent.OnColorChangeWindowOpen(
                                    viewModel.uiState.categoryColor[Category.LOCHIA]!!,
                                    Category.LOCHIA
                                )
                            )
                        }
                        Category.MOOD -> Category(
                            modifier = Modifier
                                .detectReorderAfterLongPress(reorderState)
                                .shadow(elevation.value)
                                .background(MaterialTheme.colors.surface),
                            iconID = R.drawable.ic_mood,
                            title = "Nálada",
                            primaryColor = viewModel.uiState.categoryColor[Category.MOOD]!!
                        ) {
                            viewModel.onEvent(
                                OrderCategoriesScreenUIEvent.OnColorChangeWindowOpen(
                                    viewModel.uiState.categoryColor[Category.MOOD]!!,
                                    Category.MOOD
                                )
                            )
                        }
                        Category.NOTE -> Category(
                            modifier = Modifier
                                .detectReorderAfterLongPress(reorderState)
                                .shadow(elevation.value)
                                .background(MaterialTheme.colors.surface),
                            iconID = R.drawable.ic_note,
                            title = "Poznámky",
                            primaryColor = viewModel.uiState.categoryColor[Category.NOTE]!!
                        ) {
                            viewModel.onEvent(
                                OrderCategoriesScreenUIEvent.OnColorChangeWindowOpen(
                                    viewModel.uiState.categoryColor[Category.NOTE]!!,
                                    Category.NOTE
                                )
                            )
                        }
                        Category.BREASTS -> Category(
                            modifier = Modifier
                                .detectReorderAfterLongPress(reorderState)
                                .shadow(elevation.value)
                                .background(MaterialTheme.colors.surface),
                            iconID = R.drawable.ic_bra,
                            title = "Prsníky",
                            primaryColor = viewModel.uiState.categoryColor[Category.BREASTS]!!
                        ) {
                            viewModel.onEvent(
                                OrderCategoriesScreenUIEvent.OnColorChangeWindowOpen(
                                    viewModel.uiState.categoryColor[Category.BREASTS]!!,
                                    Category.BREASTS
                                )
                            )
                        }
                        Category.TESTS -> {
                            Category(
                                modifier = Modifier
                                    .detectReorderAfterLongPress(reorderState)
                                    .shadow(elevation.value)
                                    .background(MaterialTheme.colors.surface),
                                iconID = R.drawable.ic_test,
                                title = "Testy",
                                primaryColor = viewModel.uiState.categoryColor[Category.TESTS]!!
                            ) {
                                viewModel.onEvent(
                                    OrderCategoriesScreenUIEvent.OnColorChangeWindowOpen(
                                        viewModel.uiState.categoryColor[Category.TESTS]!!,
                                        Category.TESTS
                                    )
                                )
                            }
                        }
                    }
                }
                Divider(color = Color.LightGray, thickness = 2.dp)
            }
        }
    }
    if (viewModel.uiState.ChangingColor != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.hazy)),
            contentAlignment = Alignment.Center
        ) {
            ColorPicker(
                viewModel.uiState.ChangingColor!!,
                viewModel.uiState.ChangingCategory!!,
                viewModel::onEvent
            )
        }
    }
}

@Composable
fun Category(
    modifier: Modifier,
    iconID: Int,
    title: String,
    primaryColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(horizontal = 20.dp)
            .padding(end = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
        )
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(primaryColor)
                    .clickable(onClick = onClick)
            )
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = iconID),
                contentDescription = null,
                tint = primaryColor
            )
        }
    }
}

@Composable
fun TopBar(
    navigateToCalendarScreen: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 15.dp, horizontal = 20.dp)
    ) {
        Icon(
            painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, indication = null
                ) { navigateToCalendarScreen() },
            tint = Color.Black
        )
        Text(
            text = "Ikony v kalendári",
            fontSize = 22.sp,
            fontWeight = FontWeight(700),
            modifier = Modifier.padding(start = 25.dp),
            color = Color.Black
        )
    }
}

@Composable
fun ColorPicker(
    primaryColor: Color, onCategory: Category, onEvent: (OrderCategoriesScreenUIEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .size(300.dp)
            .zIndex(10f)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onEvent(OrderCategoriesScreenUIEvent.OnColorChangeWindowClose) },
                tint = Color.Black
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            var pickedColor by remember { mutableStateOf(primaryColor) }
            ClassicColorPicker(
                modifier = Modifier.size(200.dp),
                color = primaryColor,
                onColorChanged = { color: HsvColor ->
                    pickedColor = color.toColor()
                },
                showAlphaBar = false
            )
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                ColorPickerButton(
                    text = "ZRUŠIŤ",
                    onClick = { onEvent(OrderCategoriesScreenUIEvent.OnColorChangeWindowClose) }
                )
                ColorPickerButton(
                    text = "ULOŽIŤ",
                    onClick = {
                        onEvent(
                            OrderCategoriesScreenUIEvent.OnColorChange(
                                pickedColor,
                                onCategory
                            )
                        )
                    })
            }
        }
    }
}

@Composable
fun ColorPickerButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.height(35.dp),
        elevation = null,
        shape = RoundedCornerShape(50),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.pink))
    ) {
        Text(
            text = text,
            color = Color.White,
            letterSpacing = 0.1.sp,
            fontSize = 13.sp,
            fontWeight = FontWeight(800),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}