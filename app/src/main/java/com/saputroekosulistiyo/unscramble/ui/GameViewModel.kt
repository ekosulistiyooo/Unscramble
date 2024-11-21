

package com.saputroekosulistiyo.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.saputroekosulistiyo.unscramble.data.MAX_NO_OF_WORDS
import com.saputroekosulistiyo.unscramble.data.SCORE_INCREASE
import com.saputroekosulistiyo.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

//ViewModel yang berisi data aplikasi dan metode untuk memproses data
class GameViewModel : ViewModel() {

    // Status antarmuka pengguna permainan
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    // Kumpulan kata yang digunakan dalam permainan
    private var usedWords: MutableSet<String> = mutableSetOf()
    private lateinit var currentWord: String

    init {
        resetGame()
    }

    // Menginisialisasi ulang data permainan untuk memulai ulang permainan
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    // Perbarui tebakan pengguna
    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    // Memeriksa apakah tebakan pengguna benar
    // Meningkatkan skor sesuai dengan itu
    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            // Tebakan pengguna benar, tingkatkan skor
            // dan panggil updateGameState() untuk mempersiapkan permainan untuk putaran berikutnya
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            // Tebakan pengguna salah, tampilkan error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        // Atur ulang tebakan pengguna
        updateUserGuess("")
    }


    // Lompat ke kata berikutnya
    fun skipWord() {
        updateGameState(_uiState.value.score)
        // Reset user guess
        updateUserGuess("")
    }

    // Memilih currentWord dan currentScrambledWord yang baru dan memperbarui UiState sesuai dengan
    // status permainan saat ini

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS){
            // Putaran terakhir dalam permainan, perbarui isGameOver menjadi true, jangan pilih kata baru
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            // Putaran normal dalam permainan
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Acak kata tersebut
        tempWord.shuffle()
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    private fun pickRandomWordAndShuffle(): String {
        // Terus pilih kata acak baru hingga Anda mendapatkan yang belum pernah digunakan sebelumnya
        currentWord = allWords.random()
        return if (usedWords.contains(currentWord)) {
            pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            shuffleCurrentWord(currentWord)
        }
    }
}
