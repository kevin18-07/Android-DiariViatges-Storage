package com.example.diari_viatges.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.diari_viatges.data.Difficulty
import com.example.diari_viatges.viewmodel.TravelDiaryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    viewModel: TravelDiaryViewModel,
    onGenerate: () -> Unit,
    onHistory: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {

        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        datePickerState.selectedDateMillis?.let { millis ->

                            val formatter = SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            )

                            viewModel.updateDate(
                                formatter.format(Date(millis))
                            )
                        }

                        showDatePicker = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        ) {

            DatePicker(
                state = datePickerState
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Text(
            "Diari de Viatges",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            "Crea una nova crònica de ruta"
        )

        OutlinedTextField(
            value = state.routeName,
            onValueChange = viewModel::updateRouteName,
            label = { Text("Nom de la ruta *") },
            isError = state.routeNameError != null,
            supportingText = {
                state.routeNameError?.let { Text(it) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.destination,
            onValueChange = viewModel::updateDestination,
            label = { Text("Destinació *") },
            isError = state.destinationError != null,
            supportingText = {
                state.destinationError?.let { Text(it) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            "Nivell de dificultat",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Difficulty.entries.forEach { difficulty ->

                FilterChip(
                    selected = state.difficulty == difficulty,
                    onClick = {
                        viewModel.updateDifficulty(difficulty)
                    },
                    label = {
                        Text(difficulty.displayName)
                    }
                )
            }
        }

        OutlinedTextField(
            value = state.estimatedBudget,
            onValueChange = viewModel::updateBudget,
            label = { Text("Pressupost estimat (€) *") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            isError = state.budgetError != null,
            supportingText = {
                state.budgetError?.let { Text(it) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedButton(
            onClick = {
                showDatePicker = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (state.date.isBlank())
                    "Seleccionar data"
                else
                    "Data: ${state.date}"
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text("Ruta feta en solitari")

            Switch(
                checked = state.isSolo,
                onCheckedChange = viewModel::updateSolo
            )
        }

        OutlinedTextField(
            value = state.notes,
            onValueChange = viewModel::updateNotes,
            label = { Text("Notes de l'experiència") },
            minLines = 4,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (viewModel.generateRoute()) {
                    onGenerate()
                }
            },
            enabled = viewModel.isFormValid(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generar Postal / Crònica")
        }

        OutlinedButton(
            onClick = onHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Consultar històric de rutes")
        }
    }
}