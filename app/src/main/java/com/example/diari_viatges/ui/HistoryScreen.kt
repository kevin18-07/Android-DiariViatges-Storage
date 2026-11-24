package com.example.diari_viatges.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diari_viatges.viewmodel.TravelDiaryViewModel

@Composable
fun HistoryScreen(
    viewModel: TravelDiaryViewModel,
    onRouteSelected: () -> Unit,
    onBack: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            "Històric de rutes",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.routes.isEmpty()) {

            Text("Encara no tens cap ruta guardada.")

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = state.routes,
                    key = { it.id }
                ) { route ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                viewModel.selectRoute(route)
                                onRouteSelected()
                            }
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                route.destination,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text("Ruta: ${route.routeName}")

                            Text("Data: ${route.date}")

                            Text(
                                "Dificultat: ${route.difficulty.displayName}"
                            )

                            Text(
                                "Pressupost: ${route.estimatedBudget} €"
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tornar al formulari")
        }
    }
}