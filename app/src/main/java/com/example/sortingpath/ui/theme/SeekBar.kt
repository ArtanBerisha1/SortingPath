package com.example.sortingpath.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

@Preview
@Composable
fun SeekBar(
    modifier: Modifier = Modifier
) {
    var sliderPosition by remember { mutableStateOf(1f) }
    Column(
        modifier = modifier
    ) {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                activeTrackColor = Color.Black,
                inactiveTrackColor = Color.Black,
            ),
            steps = 4,
            valueRange = 1f..6f
        )
        Text(text = sliderPosition.roundToInt().toString())
    }
}