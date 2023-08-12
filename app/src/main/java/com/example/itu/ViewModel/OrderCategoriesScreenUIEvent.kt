package com.example.itu.ViewModel

import androidx.compose.ui.graphics.Color
import com.example.itu.Model.Category
import org.burnoutcrew.reorderable.ItemPosition

// Author: Tereza Straková

sealed class OrderCategoriesScreenUIEvent {
    data class OnCategoriesReordered(val fromPos: ItemPosition, val toPos: ItemPosition) :
        OrderCategoriesScreenUIEvent()

    data class OnColorChangeWindowOpen(val currentColor: Color, val CategoryChanged: Category) :
        OrderCategoriesScreenUIEvent()

    object OnColorChangeWindowClose: OrderCategoriesScreenUIEvent()

    data class OnColorChange(val newColor: Color, val onCategory: Category) :
        OrderCategoriesScreenUIEvent()
}