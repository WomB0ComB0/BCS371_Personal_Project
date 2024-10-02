package com.example.bcs371_personal_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bcs371_personal_project.ui.screens.AuthScreen
import com.example.bcs371_personal_project.ui.screens.GameRulesScreen
import com.example.bcs371_personal_project.ui.screens.QuizScreen
import com.example.bcs371_personal_project.ui.screens.ResultsScreen
import com.example.bcs371_personal_project.ui.screens.SplashScreen
import com.example.bcs371_personal_project.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    BCS371_Personal_ProjectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            val quizViewModel: QuizViewModel = viewModel()

            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") { SplashScreen(navController) }
                composable("auth") { AuthScreen(navController) }
                composable("gameRules") { GameRulesScreen(navController) }
                composable("quiz") { QuizScreen(navController, quizViewModel) }
                composable("results") {
                    ResultsScreen(
                        score = quizViewModel.score.value,
                        totalQuestions = quizViewModel.getCurrentQuestionSize(),
                        navController = navController,
                        viewModel = quizViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun BCS371_Personal_ProjectTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}