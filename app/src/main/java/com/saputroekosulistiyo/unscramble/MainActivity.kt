package com.saputroekosulistiyo.unscramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.saputroekosulistiyo.unscramble.ui.GameScreen
import com.saputroekosulistiyo.unscramble.ui.theme.UnscrambleTheme

// MainActivity adalah activity utama yang mewarisi ComponentActivity
class MainActivity : ComponentActivity() {

    // Override onCreate untuk menginisialisasi activity
    override fun onCreate(savedInstanceState: Bundle?) {
        // Mengaktifkan mode edge-to-edge untuk tampilan fullscreen
        enableEdgeToEdge()

        // Memanggil metode onCreate dari superclass
        super.onCreate(savedInstanceState)

        // Menetapkan UI untuk activity menggunakan UnscrambleTheme dan GameScreen
        setContent {
            UnscrambleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), // Mengisi ukuran layar
                ) {
                    GameScreen() // Menampilkan layar permainan
                }
            }
        }
    }
}
