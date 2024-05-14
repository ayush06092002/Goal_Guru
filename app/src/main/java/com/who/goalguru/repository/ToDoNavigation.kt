package com.who.goalguru.repository

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.who.goalguru.navigation.ToDoScreens
import com.who.goalguru.screens.HomeScreen
import com.who.goalguru.screens.ToDoViewModel

@Composable
fun ToDoNavigation() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<ToDoViewModel>()
    NavHost(navController = navController, startDestination = ToDoScreens.HomeScreen.name ){
        composable(ToDoScreens.HomeScreen.name){
            HomeScreen(
//                viewModel = viewModel
            )
        }
        composable(ToDoScreens.AddToDoScreen.name){
            // AddToDoScreen()
        }
    }
}