package com.ap.instadiv.Screen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "tagCloud") {
        composable("tagCloud") {

            TagCloudScreen(navController)
        }
        composable("selectedTag/{tag}") { backStackEntry ->
            val tag = backStackEntry.arguments?.getString("tag")
            SelectedTagScreen(tag = tag ?: "", navController)
        }
    }
}