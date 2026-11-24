package com.example.diari_viatges.ui

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.diari_viatges.data.RouteEntry

fun generateTravelText(route: RouteEntry): String {

    return """
        DIARI DE VIATGES
        ==============================

        ${route.routeName.uppercase()}

        Destinació: ${route.destination}
        Data: ${route.date}
        Dificultat: ${route.difficulty.displayName}
        Pressupost: ${route.estimatedBudget} €

        Viatge en solitari: ${if (route.isSolo) "Sí" else "No"}

        CRÒNICA DE L'EXPERIÈNCIA
        ------------------------------

        ${route.notes}

        ==============================
        Creat amb Diari de Viatges
    """.trimIndent()
}

@Composable
fun PostalScreen(
    route: RouteEntry,
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->

        uri?.let {

            try {
                context.contentResolver.openOutputStream(it)?.use { output ->
                    output.write(
                        generateTravelText(route).toByteArray(Charsets.UTF_8)
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            "La teva postal de viatge",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    route.destination.uppercase(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                HorizontalDivider()

                Text(
                    route.routeName,
                    style = MaterialTheme.typography.titleLarge
                )

                Text("📅 Data: ${route.date}")

                SuggestionChip(
                    onClick = {},
                    label = {
                        Text(
                            "Dificultat: ${route.difficulty.displayName}"
                        )
                    }
                )

                Text("💰 Pressupost: ${route.estimatedBudget} €")

                Text(
                    if (route.isSolo)
                        "🧍 Viatge en solitari"
                    else
                        "👥 Viatge acompanyat"
                )

                HorizontalDivider()

                Text(
                    "La meva experiència",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    route.notes.ifBlank {
                        "No s'han afegit notes a aquesta ruta."
                    }
                )
            }
        }

        Button(
            onClick = {
                exportLauncher.launch(
                    "cronica_${route.routeName.replace(" ", "_")}.txt"
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Exportar Crònica (.txt)")
        }

        OutlinedButton(
            onClick = {

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        "Crònica de viatge: ${route.routeName}"
                    )
                    putExtra(
                        Intent.EXTRA_TEXT,
                        generateTravelText(route)
                    )
                }

                context.startActivity(
                    Intent.createChooser(
                        intent,
                        "Compartir crònica"
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Compartir crònica")
        }

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tornar")
        }
    }
}