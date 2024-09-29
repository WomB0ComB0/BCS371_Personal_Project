package com.example.bcs371_personal_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BCS371_Personal_ProjectTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("auth") { AuthScreen(navController) }
        composable("gameRules") { GameRulesScreen(navController) }
        composable("quiz") { QuizScreen(viewModel()) }
        composable("results") { ResultsScreen(score = 0, totalQuestions = 5, navController) }
    }
}

@Composable
fun SplashScreen(navController: androidx.navigation.NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier.align(Alignment.Center)
        )
    }
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("auth")
    }
}

@Composable
fun AuthScreen(navController: androidx.navigation.NavController) {
    var isLogin by remember { mutableStateOf(true) }
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLogin) {
            LoginForm(navController)
        } else {
            RegistrationForm(navController)
        }
        Button(onClick = { isLogin = !isLogin }) {
            Text(if (isLogin) "Switch to Register" else "Switch to Login")
        }
    }
}

@Composable
fun LoginForm(navController: androidx.navigation.NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("gameRules") }) {
            Text("Login")
        }
    }
}

@Composable
fun RegistrationForm(navController: androidx.navigation.NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Implement registration logic */ }) {
            Text("Register")
        }
    }
}

@Composable
fun GameRulesScreen(navController: androidx.navigation.NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Game Rules", style = MaterialTheme.typography.headlineMedium)
        Text("1. Answer all questions")
        Text("2. Some questions have multiple correct answers")
        Text("3. You have 30 seconds per question")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("quiz") }) {
            Text("Start Quiz")
        }
    }
}

class QuizViewModel : ViewModel() {
    private val _currentQuestion = mutableStateOf(Question("Sample question", QuestionType.MULTIPLE_CHOICE))
    val currentQuestion: State<Question> = _currentQuestion

    // Add methods to update current question, check answers, etc.
}

data class Question(val text: String, val type: QuestionType)

enum class QuestionType {
    MULTIPLE_CHOICE, MULTIPLE_ANSWER
}

@Composable
fun QuizScreen(viewModel: QuizViewModel) {
    val currentQuestion by viewModel.currentQuestion
    
    Column(modifier = Modifier.padding(16.dp)) {
        Text(currentQuestion.text, style = MaterialTheme.typography.headlineSmall)
        when (currentQuestion.type) {
            QuestionType.MULTIPLE_CHOICE -> MultipleChoiceQuestion(currentQuestion, viewModel)
            QuestionType.MULTIPLE_ANSWER -> MultipleAnswerQuestion(currentQuestion, viewModel)
        }
    }
}

@Composable
fun MultipleChoiceQuestion(question: Question, viewModel: QuizViewModel) {
    // Implement multiple choice question UI
    Text("Multiple Choice Question")
}

@Composable
fun MultipleAnswerQuestion(question: Question, viewModel: QuizViewModel) {
    // Implement multiple answer question UI
    Text("Multiple Answer Question")
}

@Composable
fun ConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Answer") },
        text = { Text("Are you sure you want to submit this answer?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ResultsScreen(score: Int, totalQuestions: Int, navController: androidx.navigation.NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Quiz Completed!", style = MaterialTheme.typography.headlineMedium)
        Text("Your score: $score / $totalQuestions")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("auth") }) {
            Text("Back to Home")
        }
    }
}

class QuizRepository(private val dataStore: DataStore<Preferences>) {
    suspend fun saveScore(score: Int) {
        dataStore.edit { preferences ->
            val currentScores = preferences[stringPreferencesKey("scores")] ?: ""
            preferences[stringPreferencesKey("scores")] = "$currentScores,$score"
        }
    }

    fun getScores(): Flow<List<Int>> = dataStore.data.map { preferences ->
        val scoresString = preferences[stringPreferencesKey("scores")] ?: ""
        scoresString.split(",").mapNotNull { it.toIntOrNull() }
    }
}

@Composable
fun ResponsiveLayout(content: @Composable () -> Unit) {
    BoxWithConstraints {
        val isPortrait = maxHeight > maxWidth
        if (isPortrait) {
            Column(modifier = Modifier.fillMaxSize()) {
                content()
            }
        } else {
            Row(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    }
}
