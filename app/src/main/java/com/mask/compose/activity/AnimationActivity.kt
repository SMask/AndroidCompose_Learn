package com.mask.compose.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mask.compose.enums.AnimationType
import com.mask.compose.ui.theme.AndroidCompose_DemoTheme
import com.mask.compose.ui.theme.Dimen
import com.mask.compose.utils.ActivityUtils
import com.mask.compose.utils.LogUtils

class AnimationActivity : ComponentActivity() {

    companion object {
        fun startActivity(context: Context) {
            ActivityUtils.startActivity(context, AnimationActivity::class.java)
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
                    AnimationLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimationLayoutPreview() {
    AndroidCompose_DemoTheme {
        AnimationLayout()
    }
}

@Composable
fun AnimationLayout(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationLayout")

    val selectedTab = remember { mutableStateOf(AnimationType.AnimatedVisibility) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AnimationTab(
            modifier = Modifier
                .fillMaxWidth(),
            selectedTab = selectedTab
        )
    }
}

@Composable
fun AnimationTab(selectedTab: MutableState<AnimationType>, modifier: Modifier = Modifier) {
    LogUtils.i("AnimationTab selectedTab: ${selectedTab.value}")

    LazyRow(
        modifier = modifier
            .background(Color(0x80CCCCCC))
            .padding(Dimen.padding),
        horizontalArrangement = Arrangement.spacedBy(Dimen.padding)
    ) {
        items(AnimationType.entries) { data ->
            val isSelected by remember { derivedStateOf { selectedTab.value == data } }
            AnimationTabItem(
                data = data,
                isSelected = isSelected,
                onClick = {
                    selectedTab.value = data
                }
            )
        }
    }
}

@Composable
fun AnimationTabItem(data: AnimationType, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    LogUtils.i("AnimationTabItem data: $data isSelected: $isSelected")

    val btnBgColor = if (isSelected) Color.Black else Color(0xFFF7F7F7)
    val textColor = if (isSelected) Color.White else Color.Black
    Button(
        modifier = modifier
            .height(Dimen.buttonHeight),
        colors = ButtonDefaults.buttonColors(containerColor = btnBgColor),
        onClick = onClick
    ) {
        Text(
            color = textColor,
            text = stringResource(data.textResId)
        )
    }
}

@Composable
fun AnimationAnimatedVisibility(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationAnimatedVisibility")

    Text(
        text = "AnimatedVisibility"
    )
}

@Composable
fun AnimationAnimateContentSize(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationAnimateContentSize")

    Text(
        text = "AnimateContentSize"
    )
}

@Composable
fun AnimationAnimateAsState(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationAnimateAsState")

    Text(
        text = "AnimateAsState"
    )
}

@Composable
fun AnimationInfiniteTransition(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationInfiniteTransition")

    Text(
        text = "InfiniteTransition"
    )
}