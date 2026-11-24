package com.example.diari_viatges.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class TravelDiaryRepository(context: Context) {

    private val file = File(
        context.filesDir,
        "diari_viatges.json"
    )

    fun saveRoutes(routes: List<RouteEntry>) {
        val jsonArray = JSONArray()

        routes.forEach { route ->

            val jsonObject = JSONObject().apply {
                put("id", route.id)
                put("routeName", route.routeName)
                put("destination", route.destination)
                put("difficulty", route.difficulty.name)
                put("estimatedBudget", route.estimatedBudget)
                put("date", route.date)
                put("isSolo", route.isSolo)
                put("notes", route.notes)
            }

            jsonArray.put(jsonObject)
        }

        file.writeText(jsonArray.toString(4))
    }

    fun loadRoutes(): List<RouteEntry> {

        if (!file.exists()) {
            return emptyList()
        }

        return try {

            val jsonArray = JSONArray(file.readText())
            val routes = mutableListOf<RouteEntry>()

            for (i in 0 until jsonArray.length()) {

                val obj = jsonArray.getJSONObject(i)

                routes.add(
                    RouteEntry(
                        id = obj.getString("id"),
                        routeName = obj.getString("routeName"),
                        destination = obj.getString("destination"),
                        difficulty = Difficulty.valueOf(
                            obj.getString("difficulty")
                        ),
                        estimatedBudget = obj.getDouble("estimatedBudget"),
                        date = obj.getString("date"),
                        isSolo = obj.getBoolean("isSolo"),
                        notes = obj.getString("notes")
                    )
                )
            }

            routes

        } catch (e: Exception) {
            emptyList()
        }
    }
}