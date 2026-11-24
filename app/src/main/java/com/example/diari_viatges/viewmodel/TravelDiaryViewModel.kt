package com.example.diari_viatges.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.diari_viatges.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class TravelDiaryViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = TravelDiaryRepository(application)

    private val _uiState = MutableStateFlow(
        TravelDiaryUiState()
    )

    val uiState: StateFlow<TravelDiaryUiState> =
        _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val routes = withContext(Dispatchers.IO) {
                repository.loadRoutes()
            }

            _uiState.update {
                it.copy(routes = routes)
            }
        }
    }

    fun updateRouteName(value: String) {
        _uiState.update {
            it.copy(
                routeName = value,
                routeNameError = null
            )
        }
    }

    fun updateDestination(value: String) {
        _uiState.update {
            it.copy(
                destination = value,
                destinationError = null
            )
        }
    }

    fun updateDifficulty(value: Difficulty) {
        _uiState.update {
            it.copy(difficulty = value)
        }
    }

    fun updateBudget(value: String) {
        _uiState.update {
            it.copy(
                estimatedBudget = value,
                budgetError = null
            )
        }
    }

    fun updateDate(value: String) {
        _uiState.update {
            it.copy(date = value)
        }
    }

    fun updateSolo(value: Boolean) {
        _uiState.update {
            it.copy(isSolo = value)
        }
    }

    fun updateNotes(value: String) {
        _uiState.update {
            it.copy(notes = value)
        }
    }

    fun validateForm(): Boolean {

        val state = _uiState.value

        val budget = state.estimatedBudget
            .replace(",", ".")
            .toDoubleOrNull()

        val nameError =
            if (state.routeName.isBlank())
                "El nombre de la ruta es obligatorio"
            else null

        val destinationError =
            if (state.destination.isBlank())
                "El destino es obligatorio"
            else null

        val budgetError =
            if (budget == null || !budget.isFinite() || budget < 0)
                "Introduce un presupuesto válido"
            else null

        _uiState.update {
            it.copy(
                routeNameError = nameError,
                destinationError = destinationError,
                budgetError = budgetError
            )
        }

        return nameError == null &&
                destinationError == null &&
                budgetError == null
    }

    fun isFormValid(): Boolean {
        val state = _uiState.value

        val budget = state.estimatedBudget
            .replace(",", ".")
            .toDoubleOrNull()

        return state.routeName.isNotBlank() &&
                state.destination.isNotBlank() &&
                budget != null &&
                budget.isFinite() &&
                budget >= 0
    }

    fun generateRoute(): Boolean {

        if (!validateForm()) return false

        val state = _uiState.value

        val budget = state.estimatedBudget
            .replace(",", ".")
            .toDoubleOrNull() ?: return false

        val route = RouteEntry(
            id = UUID.randomUUID().toString(),
            routeName = state.routeName.trim(),
            destination = state.destination.trim(),
            difficulty = state.difficulty,
            estimatedBudget = budget,
            date = state.date,
            isSolo = state.isSolo,
            notes = state.notes.trim()
        )

        val updatedRoutes = listOf(route) + state.routes

        _uiState.update {
            it.copy(
                routes = updatedRoutes,
                selectedRoute = route
            )
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.saveRoutes(updatedRoutes)
            }
        }

        return true
    }

    fun selectRoute(route: RouteEntry) {
        _uiState.update {
            it.copy(selectedRoute = route)
        }
    }

    fun resetForm() {
        _uiState.update {
            it.copy(
                routeName = "",
                destination = "",
                difficulty = Difficulty.FACIL,
                estimatedBudget = "",
                date = "",
                isSolo = false,
                notes = "",
                routeNameError = null,
                destinationError = null,
                budgetError = null
            )
        }
    }
}