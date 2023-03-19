package com.example.sortingpath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sortingpath.ui.theme.MainScreen
import com.example.sortingpath.ui.theme.SortingPathTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SortingPathTheme {
                MainScreen()
            }
        }
    }
}