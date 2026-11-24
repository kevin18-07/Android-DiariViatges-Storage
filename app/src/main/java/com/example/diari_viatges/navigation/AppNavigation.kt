package com.example.diari_viatges.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diari_viatges.ui.FormScreen
import com.example.diari_viatges.ui.PostalScreen
import com.example.diari_viatges.ui.HistoryScreen
import com.example.diari_viatges.viewmodel.TravelDiaryViewModel

@Composable
fun AppNavigation(
    viewModel: TravelDiaryViewModel
) {

    val navController = rememberNavController()

    val state by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "form"
    ) {

        composable("form") {

            FormScreen(
                viewModel = viewModel,

                onGenerate = {
                    navController.navigate("postal")
                },

                onHistory = {
                    navController.navigate("history")
                }
            )
        }

        composable("postal") {

            state.selectedRoute?.let { route ->

                PostalScreen(
                    route = route,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable("history") {

            HistoryScreen(
                viewModel = viewModel,

                onRouteSelected = {
                    navController.navigate("postal")
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}