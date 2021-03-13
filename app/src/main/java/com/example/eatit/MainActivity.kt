package com.example.eatit

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eatit.screen.Screen
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
	val navController = rememberNavController()
	EatItTheme {
		ProvideWindowInsets {
			NavHost(navController = navController, startDestination = Screen.Main.destination) {
				composable(Screen.Main.destination) {
					MainScreen()
				}
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	App()
}