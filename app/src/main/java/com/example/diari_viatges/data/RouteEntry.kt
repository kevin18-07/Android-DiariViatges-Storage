package com.example.diari_viatges.data

data class RouteEntry(
    val id: String,
    val routeName: String,
    val destination: String,
    val difficulty: Difficulty,
    val estimatedBudget: Double,
    val date: String,
    val isSolo: Boolean,
    val notes: String
)

enum class Difficulty(val displayName: String) {
    FACIL("Fácil"),
    MODERADA("Moderada"),
    EXIGENT("Exigente")
}

data class TravelDiaryUiState(
    val routeName: String = "",
    val destination: String = "",
    val difficulty: Difficulty = Difficulty.FACIL,
    val estimatedBudget: String = "",
    val date: String = "",
    val isSolo: Boolean = false,
    val notes: String = "",

    val routeNameError: String? = null,
    val destinationError: String? = null,
    val budgetError: String? = null,

    val routes: List<RouteEntry> = emptyList(),
    val selectedRoute: RouteEntry? = null
)