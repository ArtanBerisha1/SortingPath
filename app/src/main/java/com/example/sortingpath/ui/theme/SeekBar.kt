package com.example.sortingpath.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

@Composable
fun SeekBar(
    modifier: Modifier = Modifier,
    onValueChangeListener: (Int) -> Unit
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
            valueRange = 1f..6f,
            onValueChangeFinished = {
                onValueChangeListener(sliderPosition.toInt())
            }
        )
        Text(text = "${sliderPosition.roundToInt()}x")
    }
}