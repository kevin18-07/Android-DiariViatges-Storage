package com.example.diari_viatges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.diari_viatges.navigation.AppNavigation
import com.example.diari_viatges.viewmodel.TravelDiaryViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val diaryViewModel: TravelDiaryViewModel = viewModel()

            MaterialTheme {

                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                    AppNavigation(
                        viewModel = diaryViewModel
                    )
                }
            }
        }
    }
}