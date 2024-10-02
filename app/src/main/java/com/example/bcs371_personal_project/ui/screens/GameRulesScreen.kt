package com.example.bcs371_personal_project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun GameRulesScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Game Rules", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("1. Answer all questions")
        Text("2. Some questions have multiple correct answers")
        Text("3. You have 30 seconds per question")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("quiz") }) {
            Text("Start Quiz")
        }
    }
}