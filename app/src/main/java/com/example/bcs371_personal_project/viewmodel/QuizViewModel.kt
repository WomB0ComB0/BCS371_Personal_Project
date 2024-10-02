package com.example.bcs371_personal_project.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuizViewModel : ViewModel() {
    private val questions = listOf(
        Question("What is the capital of France?", QuestionType.MULTIPLE_CHOICE,
            listOf("London", "Berlin", "Paris", "Madrid"), listOf(2)),
        Question("Which of these are primary colors?", QuestionType.MULTIPLE_ANSWER,
            listOf("Red", "Green", "Blue", "Yellow"), listOf(0, 2, 3)),
        Question("What is 2 + 2?", QuestionType.MULTIPLE_CHOICE,
            listOf("3", "4", "5", "6"), listOf(1)),
        Question("Select all prime numbers:", QuestionType.MULTIPLE_ANSWER,
            listOf("2", "3", "4", "5"), listOf(0, 1, 3)),
        Question("Who wrote 'Romeo and Juliet'?", QuestionType.MULTIPLE_CHOICE,
            listOf("Charles Dickens", "William Shakespeare", "Jane Austen", "Mark Twain"), listOf(1))
    )

    private val _currentQuestion = mutableStateOf(questions[0])
    val currentQuestion: State<Question> = _currentQuestion

    private val _currentQuestionIndex = mutableStateOf(0)
    private val _score = mutableStateOf(0)
    private val _quizCompleted = mutableStateOf(false)
    private val _selectedAnswers = MutableStateFlow<List<Int>>(emptyList())

    val score: State<Int> = _score
    val quizCompleted: State<Boolean> = _quizCompleted
    val selectedAnswers: StateFlow<List<Int>> = _selectedAnswers.asStateFlow()

    fun selectAnswer(index: Int) {
        val currentAnswers = _selectedAnswers.value.toMutableList()
        if (currentQuestion.value.type == QuestionType.MULTIPLE_CHOICE) {
            _selectedAnswers.value = listOf(index)
        } else {
            if (index in currentAnswers) {
                currentAnswers.remove(index)
            } else {
                currentAnswers.add(index)
            }
            _selectedAnswers.value = currentAnswers
        }
    }

    fun submitAnswer() {
        val currentQuestion = questions[_currentQuestionIndex.value]
        if (_selectedAnswers.value == currentQuestion.correctAnswers) {
            _score.value++
        }
        moveToNextQuestion()
    }

    private fun moveToNextQuestion() {
        if (_currentQuestionIndex.value < questions.size - 1) {
            _currentQuestionIndex.value++
            _currentQuestion.value = questions[_currentQuestionIndex.value]
            _selectedAnswers.value = emptyList()
        } else {
            _quizCompleted.value = true
        }
    }

    fun resetQuiz() {
        _currentQuestionIndex.value = 0
        _score.value = 0
        _quizCompleted.value = false
        _selectedAnswers.value = emptyList()
        _currentQuestion.value = questions[0]
    }

    fun getCurrentQuestionSize(): Int {
        return questions.size
    }
}

data class Question(
    val text: String,
    val type: QuestionType,
    val options: List<String>,
    val correctAnswers: List<Int>
)

enum class QuestionType {
    MULTIPLE_CHOICE, MULTIPLE_ANSWER
}