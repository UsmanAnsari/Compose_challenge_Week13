package com.uansari.composechallengeweek13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uansari.composechallengeweek13.ui.theme.ComposeChallengeWeek13Theme
import com.uansari.composechallengeweek13.ui.theme.Teal200

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChallengeWeek13Theme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            TaskList {
                                if (it == "Slide to Unlock") {
                                    navController.navigate("swipe")
                                }
                            }
                        }
                        composable("swipe") {
                            Greeting("Android")
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
            .padding(horizontal = 4.dp)
    ) {
        Spacer(Modifier.size(4.dp))
        Button(
            onClick = { onClick(name) },
            contentPadding = PaddingValues(all = 24.dp),
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ComposeChallengeWeek13Theme {
        TaskList() {}
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeChallengeWeek13Theme {
        Greeting("Android")
    }
}