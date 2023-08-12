package com.example.itu.ViewModel

import androidx.compose.ui.graphics.Color
import com.example.itu.Model.Category

// Author: Tereza Straková

data class OrderCategoriesUIState(
    val categoryOrder: MutableList<Category> = mutableListOf(),
    val ChangingColor: Color? = null,
    val categoryColor: Map<Category, Color> = mapOf(),
    val ChangingCategory: Category? = null
)
