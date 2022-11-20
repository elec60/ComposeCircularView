package com.example.composecircularview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecircularview.ui.theme.ComposeCircularViewTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val random = Random
        setContent {
            ComposeCircularViewTheme {
                CircularView {
                    repeat(10) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(
                                        red = random.nextInt(255),
                                        green = random.nextInt(255),
                                        blue = random.nextInt(255)
                                    ), shape = CircleShape
                                )
                                .size(50.dp),
                            contentAlignment = Alignment.Center

                        ) {
                            Text(text = it.toString(), fontSize = 12.sp, color = Color.White)
                        }
                    }

                }
            }
        }
    }
}

