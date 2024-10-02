package com.example.bcs371_personal_project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bcs371_personal_project.viewmodel.QuizViewModel

@Composable
fun ResultsScreen(score: Int, totalQuestions: Int, navController: NavController, viewModel: QuizViewModel = viewModel()) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Quiz Completed!", style = MaterialTheme.typography.headlineMedium)
        Text("Your score: $score / $totalQuestions")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.resetQuiz()
                navController.navigate("quiz")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retry Quiz")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { navController.navigate("auth") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}