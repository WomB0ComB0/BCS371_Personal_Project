package com.example.bcs371_personal_project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bcs371_personal_project.viewmodel.Question
import com.example.bcs371_personal_project.viewmodel.QuestionType
import com.example.bcs371_personal_project.viewmodel.QuizViewModel

@Composable
fun QuizScreen(navController: NavController, viewModel: QuizViewModel = viewModel()) {
    val currentQuestion by viewModel.currentQuestion
    val quizCompleted by viewModel.quizCompleted
    val score by viewModel.score

    if (quizCompleted) {
        ResultsScreen(score = score, totalQuestions = 5, navController = navController)
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(currentQuestion.text, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            when (currentQuestion.type) {
                QuestionType.MULTIPLE_CHOICE -> MultipleChoiceQuestion(currentQuestion, viewModel)
                QuestionType.MULTIPLE_ANSWER -> MultipleAnswerQuestion(currentQuestion, viewModel)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.submitAnswer() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Answer")
            }
        }
    }
}

@Composable
fun MultipleChoiceQuestion(question: Question, viewModel: QuizViewModel) {
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()

    Column {
        question.options.forEachIndexed { index, option ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = selectedAnswers.contains(index),
                    onClick = { viewModel.selectAnswer(index) }
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun MultipleAnswerQuestion(question: Question, viewModel: QuizViewModel) {
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()

    Column {
        question.options.forEachIndexed { index, option ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Checkbox(
                    checked = selectedAnswers.contains(index),
                    onCheckedChange = { viewModel.selectAnswer(index) }
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}