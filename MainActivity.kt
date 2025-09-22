package com.example.karibu_android_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karibu_android_app.ui.theme.Karibu_Android_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Karibu_Android_AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->
                    ProMainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProMainScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("Rejoice in the Lord always!") }
    var showExtraMessage by remember { mutableStateOf(false) }
    var bgColorIndex by remember { mutableStateOf(0) }
    var extraMessageIndex by remember { mutableStateOf(0) }

    val bgColors = listOf(
        Color(0xFFE3F2FD),
        Color(0xFFFFF3E0),
        Color(0xFFE8F5E9),
        Color(0xFFFCE4EC)
    )

    val extraMessages = listOf(
        "May your day be filled with joy and peace! ✨",
        "Believe in yourself and all that you are. 💫",
        "Good things are coming your way today! 🌞",
        "Keep smiling, the world is brighter with you. 😊",
        "Trust the process and enjoy the journey. 🌿"
    )

    // Animated background and main layout
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgColors[bgColorIndex])
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Logo with shadow and rounded corners
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .size(180.dp)
                    .shadow(8.dp, RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Animated greeting text
            AnimatedContent(
                targetState = message,
                transitionSpec = {
                    fadeIn(animationSpec = tween(600)) with
                            fadeOut(animationSpec = tween(600))
                }
            ) { targetMessage ->
                Text(
                    text = targetMessage,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xF90D47A1),
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Name input field
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Enter your name") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF0D47A1),
                    unfocusedIndicatorColor = Color(0xFF90CAF9),
                    cursorColor = Color(0xFF0D47A1),
                    focusedTextColor = Color.Black
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Greet button
                Button(
                    onClick = {
                        message = if (name.isBlank()) "Rejoice in the Lord always!" else "Hello $name!"
                        showExtraMessage = false
                        bgColorIndex = (bgColorIndex + 1) % bgColors.size
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF1976D2)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Greet Me", color = Color.White)
                }

                // Toggle extra message button
                Button(
                    onClick = {
                        showExtraMessage = !showExtraMessage
                        if (showExtraMessage) {
                            extraMessageIndex = (extraMessageIndex + 1) % extraMessages.size
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF43A047)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Toggle Blessing", color = Color.White)
                }
            }

            // Animated extra message
            AnimatedVisibility(
                visible = showExtraMessage,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                Text(
                    text = extraMessages[extraMessageIndex],
                    fontSize = 22.sp,
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProMainScreenPreview() {
    Karibu_Android_AppTheme {
        ProMainScreen()
    }
}
