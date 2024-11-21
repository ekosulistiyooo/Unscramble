package com.saputroekosulistiyo.unscramble.data

const val MAX_NO_OF_WORDS = 10
const val SCORE_INCREASE = 20

// Daftar yang berisi semua kata untuk Permainan
val allWords: Set<String> =
    setOf(
        "at",
        "sea",
        "home",
        "arise",
        "banana",
        "android",
        "birthday",
        "briefcase",
        "motorcycle",
        "cauliflower"
    )


 // Memetakan kata-kata ke panjangnya. Setiap kata dalam allWords memiliki panjang yang unik. Ini diperlukan karena
 // kata-kata dipilih secara acak di dalam GameViewModel dan pemilihannya tidak dapat diprediksi
private val wordLengthMap: Map<Int, String> = allWords.associateBy({ it.length }, { it })

internal fun getUnscrambledWord(scrambledWord: String) = wordLengthMap[scrambledWord.length] ?: ""
