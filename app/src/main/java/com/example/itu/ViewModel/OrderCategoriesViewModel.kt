package com.example.itu.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itu.Model.Category
import com.example.itu.Model.Repository
import kotlinx.coroutines.launch

// Author: Tereza Straková

class OrderCategoriesViewModel(
    private val repository: Repository
) : ViewModel(), DefaultLifecycleObserver {
    var uiState by mutableStateOf(OrderCategoriesUIState())
        private set

    init {
        getCategoryOrder()
        getCategoryColor()
    }

    override fun onPause(owner: LifecycleOwner) {
        repository.saveDataAsJSON()
        super.onResume(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        repository.saveDataAsJSON()
        super.onResume(owner)
    }

    fun onEvent(event: OrderCategoriesScreenUIEvent) {
        when (event) {
            is OrderCategoriesScreenUIEvent.OnCategoriesReordered -> {
                moveCategoryOrder(event.fromPos.index,event.toPos.index)
            }
            is OrderCategoriesScreenUIEvent.OnColorChangeWindowOpen -> {
                setChangingVariables(event.currentColor, event.CategoryChanged)
            }
            is OrderCategoriesScreenUIEvent.OnColorChangeWindowClose -> {
                setChangingVariables(null,null)
            }
            is OrderCategoriesScreenUIEvent.OnColorChange -> {
                setColorForCategory(event.newColor, event.onCategory)
            }
        }
    }

    private fun setColorForCategory(newColor: Color, onCategory: Category) {
        viewModelScope.launch {
            repository.setColorForCategory(newColor, onCategory)
            val editedColors = repository.getCategoryColor()
            uiState = uiState.copy(
                ChangingColor = null,
                ChangingCategory = null,
                categoryColor = editedColors
            )
        }
    }

    private fun setChangingVariables(currentColor: Color?, categoryChanged: Category?) {
        viewModelScope.launch {
            uiState = uiState.copy(ChangingColor = currentColor, ChangingCategory = categoryChanged)
        }
    }

    private fun getCategoryOrder() {
        uiState = uiState.copy(categoryOrder = repository.getCategoryOrder())
    }

    private fun getCategoryColor() {
        uiState = uiState.copy(categoryColor = repository.getCategoryColor())
    }

    private fun moveCategoryOrder(fromPos: Int, toPos: Int){
        viewModelScope.launch {
            repository.moveCategoryOrder(fromPos, toPos)
            val editedOrder = repository.getCategoryOrder()
            uiState = uiState.copy(categoryOrder = editedOrder)
        }
    }
}