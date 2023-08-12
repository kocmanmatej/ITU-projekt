package com.example.itu.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.itu.Model.Repository
import com.example.itu.View.*
import com.example.itu.ViewModel.CalendarScreenViewModel
import com.example.itu.ViewModel.DayDetailViewModel
import com.example.itu.ViewModel.InputScreenViewModel
import com.example.itu.ViewModel.SexInputScreenViewModel
import com.example.itu.ViewModel.TesInputScreenViewModel
import com.example.itu.ViewModel.OrderCategoriesViewModel

// Authors: Matej Kocman

@Composable
fun Navigation() {
    val repository = Repository()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "CalendarScreen") {
        composable("CalendarScreen") {
            CalendarScreen(
                viewModel = viewModel { CalendarScreenViewModel(repository) },
                navigateToInputScreen = { year: Int, month: Int, day: Int ->
                    navController.navigate("DayDetailScreen/${year}/${month}/${day}")
                },
                navigateToOrderCategoriesScreen = {
                    navController.navigate("OrderCategoriesScreen")
                })
        }
        composable("OrderCategoriesScreen") {
            OrderCategoriesScreen(
                viewModel = viewModel { OrderCategoriesViewModel(repository) },
                navigateToCalendarScreen = { navController.popBackStack() })
        }
        composable(
            "InputScreen/{selectedDayYear}/{selectedDayMonth}/{selectedDayDate}/{selectedInput}",
            arguments = listOf(
                navArgument("selectedDayYear") { type = NavType.IntType },
                navArgument("selectedDayMonth") { type = NavType.IntType },
                navArgument("selectedDayDate") { type = NavType.IntType },
                navArgument("selectedInput") { type = NavType.StringType }
            ),
        ) { entry ->
            InputScreen(
                viewModel = viewModel {
                    InputScreenViewModel(
                        repository,
                        entry.arguments!!.getInt("selectedDayYear"),
                        entry.arguments!!.getInt("selectedDayMonth"),
                        entry.arguments!!.getInt("selectedDayDate"),
                        entry.arguments!!.getString("selectedInput")
                    )
                },
                navigateToCalendarScreen = { year: Int, month: Int, day: Int ->
                    navController.navigate("DayDetailScreen/${year}/${month}/${day}") }
            )
            entry.lifecycle.addObserver(LifecycleObserver(repository))
        }
        composable(
            "DayDetailScreen/{selectedDayYear}/{selectedDayMonth}/{selectedDayDate}",
            arguments = listOf(
                navArgument("selectedDayYear") { type = NavType.IntType },
                navArgument("selectedDayMonth") { type = NavType.IntType },
                navArgument("selectedDayDate") { type = NavType.IntType })
        ) { entry ->
            DayDetail(
                viewModel = viewModel {
                    DayDetailViewModel(
                        repository,
                        entry.arguments!!.getInt("selectedDayYear"),
                        entry.arguments!!.getInt("selectedDayMonth"),
                        entry.arguments!!.getInt("selectedDayDate")
                    )
                },
                navigateToCalendarScreen = { navController.navigate("CalendarScreen") },
                navigateToInputScreen = { year: Int, month: Int, day: Int, input: String ->
                    navController.navigate("InputScreen/${year}/${month}/${day}/${input}") },
                navigateToSexInputScreen = {year: Int, month: Int, day: Int, index: Int ->
                    navController.navigate("SexInputScreen/${year}/${month}/${day}/${index}")
                },
                navigateToTestInputScreen = { year: Int, month: Int, day: Int ->
                    navController.navigate("TestInputScreen/${year}/${month}/${day}") },

            )
            entry.lifecycle.addObserver(LifecycleObserver(repository))
        }

        composable(
            "SexInputScreen/{selectedDayYear}/{selectedDayMonth}/{selectedDayDate}/{selectedInput}",
            arguments = listOf(
                navArgument("selectedDayYear") { type = NavType.IntType },
                navArgument("selectedDayMonth") { type = NavType.IntType },
                navArgument("selectedDayDate") { type = NavType.IntType },
                navArgument("selectedInput") { type = NavType.IntType }
            ),
        ) { entry ->
            SexInputScreen(
                viewModel = viewModel {
                    SexInputScreenViewModel(
                        repository,
                        entry.arguments!!.getInt("selectedDayYear"),
                        entry.arguments!!.getInt("selectedDayMonth"),
                        entry.arguments!!.getInt("selectedDayDate"),
                        entry.arguments!!.getInt("selectedInput")
                    )
                },
                navigateToCalendarScreen = { year: Int, month: Int, day: Int ->
                    navController.navigate("DayDetailScreen/${year}/${month}/${day}") }
            )
            entry.lifecycle.addObserver(LifecycleObserver(repository))
        }

        composable(
            "TestInputScreen/{selectedDayYear}/{selectedDayMonth}/{selectedDayDate}",
            arguments = listOf(
                navArgument("selectedDayYear") { type = NavType.IntType },
                navArgument("selectedDayMonth") { type = NavType.IntType },
                navArgument("selectedDayDate") { type = NavType.IntType }
            ),
        ) { entry ->
            TestInputScreen(
                viewModel = viewModel {
                    TesInputScreenViewModel(
                        repository,
                        entry.arguments!!.getInt("selectedDayYear"),
                        entry.arguments!!.getInt("selectedDayMonth"),
                        entry.arguments!!.getInt("selectedDayDate")
                    )
                },
                navigateToCalendarScreen = { year: Int, month: Int, day: Int ->
                    navController.navigate("DayDetailScreen/${year}/${month}/${day}") }
            )
            entry.lifecycle.addObserver(LifecycleObserver(repository))
        }
    }
}