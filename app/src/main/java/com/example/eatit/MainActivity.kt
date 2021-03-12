package com.example.eatit

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.eatit.screen.main.MainScreen
import com.example.eatit.ui.EatItTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			App()
		}
	}
}

@Composable
fun App() {
	EatItTheme {
		ProvideWindowInsets {
			MainScreen()
		}
	}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	App()
}