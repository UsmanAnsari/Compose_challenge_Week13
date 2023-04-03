package com.uansari.composechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.SwipeRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uansari.composechallenge.ui.theme.ComposeChallengeTheme
import com.uansari.composechallenge.ui.theme.Purple200
import com.uansari.composechallenge.ui.theme.Teal200
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeChallengeTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            TaskList() {
                                if (it == "Slide to Unlock") {
                                    navController.navigate("swipe")
                                }
                            }
                        }
                        composable("swipe") {
                            ComposeChallengeTheme{
                                Text(text = "woh hi")
                            }
/*
                            SwipeButton(text = "Swipe to unlock") {
                                navController.navigate("swipe")
                            }
*/
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskList(
    names: List<String> = arrayListOf(
        "Slide to Unlock",
        "Task 1",
        "Task 2",
        "Task 3",
        "Task 4",
        "Task 5",
    ),
    onClick: (name: String) -> Unit
) {
    Surface {
        LazyColumn(Modifier.padding(vertical = 4.dp)) {
            items(items = names) { name ->
                TaskItem(name = name, onClick)
            }
        }
    }
}

@Composable
fun TaskItem(name: String, onClick: (name: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Spacer(Modifier.size(8.dp))
        Button(
            onClick = { onClick(name) },
            contentPadding = PaddingValues(all = 20.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Teal200)
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    ComposeChallengeTheme {
        TaskList(onClick = {})
    }
}