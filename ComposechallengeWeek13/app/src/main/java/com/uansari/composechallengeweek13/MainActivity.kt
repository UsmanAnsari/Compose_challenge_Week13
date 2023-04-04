package com.uansari.composechallengeweek13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uansari.composechallengeweek13.ui.theme.ComposeChallengeWeek13Theme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChallengeWeek13Theme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
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
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .fillMaxHeight(),
                            ) {
                                SwipeButton(
                                    modifier = Modifier.align(Alignment.Center),
                                ) {
                                    navController.popBackStack()
                                }
                            }
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
    ), onClick: (name: String) -> Unit
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
        Spacer(Modifier.size(4.dp))
        Button(
            onClick = { onClick(name) },
            contentPadding = PaddingValues(all = 24.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.LightGray),
            border = BorderStroke(2.dp, Color.DarkGray),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.DarkGray
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    borderStroke: BorderStroke = BorderStroke(2.dp, Color.DarkGray),
    elevation: Dp = 8.dp,
    textStyle: TextStyle = TextStyle(Color.DarkGray, 20.sp),
    onSwipe: () -> Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val textAlpha by animateFloatAsState(
        if (swipeableState.offset.value > 10f) (1 - swipeableState.progress.fraction) else 1f
    )

    if (swipeableState.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                if (swipeableState.currentValue == 1) {
                    onSwipe()
                }
            }
        }
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        border = borderStroke,
        elevation = elevation,
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp),
        ) {
            var iconSize by remember { mutableStateOf(IntSize.Zero) }
            val maxWidth = with(LocalDensity.current) {
                this@BoxWithConstraints.maxWidth.toPx() - iconSize.width
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.End,
                text = "slide to unlock",
                style = textStyle.copy(color = textStyle.color.copy(alpha = textAlpha))
            )
            Box(modifier = Modifier
                .onGloballyPositioned {
                    iconSize = it.size
                }
                .swipeable(
                    state = swipeableState,
                    anchors = mapOf(
                        0f to 0, maxWidth to 1
                    ),
                    thresholds = { _, _ -> FractionalThreshold(0.9f) },
                    orientation = Orientation.Horizontal
                )
                .offset {
                    IntOffset(swipeableState.offset.value.roundToInt(), 0)
                }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.DarkGray, shape = CircleShape),
                )
            }
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
        SwipeButton {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeChallengeWeek13Theme {
        Greeting("Android")
    }
}