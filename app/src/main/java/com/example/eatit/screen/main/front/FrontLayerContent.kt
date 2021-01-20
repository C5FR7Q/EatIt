package com.example.eatit.screen.main.front

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.eatit.screen.main.FrontScreen

@Composable
fun FrontLayerContent(frontScreen: FrontScreen) {
	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
		Text(text = "Feed")
	}
}