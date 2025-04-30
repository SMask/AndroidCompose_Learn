package com.mask.compose.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mask.compose.ui.theme.AndroidCompose_DemoTheme
import com.mask.compose.ui.theme.Dimen
import com.mask.compose.ui.theme.Style
import kotlin.math.roundToInt

class DraggableViewActivity : ComponentActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, DraggableViewActivity::class.java)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidCompose_DemoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    DraggableViewLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun DraggableViewLayout(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        DraggableHorizontal(
            modifier = Modifier.align(alignment = Alignment.TopCenter)
        )
        DraggableVertical(
            modifier = Modifier.align(alignment = Alignment.CenterStart)
        )
        Draggable(
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}

@Composable
fun DraggableHorizontal(modifier: Modifier = Modifier) {
    var xOffset by remember { mutableFloatStateOf(0f) }
    val xText = "水平拖拽"

    Box(
        modifier = modifier
            .padding(top = Dimen.padding)
            .offset {
                IntOffset(xOffset.roundToInt(), 0)
            }
            .size(100.dp)
            .background(Color(0x80FF0000))
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    xOffset += delta
                }
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            style = Style.TextStyle.CONTENT,
            text = xText
        )
    }
}

@Composable
fun DraggableVertical(modifier: Modifier = Modifier) {
    var yOffset by remember { mutableFloatStateOf(0f) }
    val yText = "垂直拖拽"

    Box(
        modifier = modifier
            .padding(start = Dimen.padding)
            .offset {
                IntOffset(0, yOffset.roundToInt())
            }
            .size(100.dp)
            .background(Color(0x8000FF00))
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    yOffset += delta
                }
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            style = Style.TextStyle.CONTENT,
            text = yText
        )
    }
}

@Composable
fun Draggable(modifier: Modifier = Modifier) {
    var xOffset by remember { mutableFloatStateOf(0f) }
    var yOffset by remember { mutableFloatStateOf(0f) }
    val text = "任意拖拽"

    Box(
        modifier = modifier
            .offset {
                IntOffset(xOffset.roundToInt(), yOffset.roundToInt())
            }
            .size(100.dp)
            .background(Color(0x800000FF))
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    xOffset += dragAmount.x
                    yOffset += dragAmount.y
                }
            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            style = Style.TextStyle.CONTENT,
            text = text
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DraggableViewLayoutPreview() {
    AndroidCompose_DemoTheme {
        DraggableViewLayout()
    }
}