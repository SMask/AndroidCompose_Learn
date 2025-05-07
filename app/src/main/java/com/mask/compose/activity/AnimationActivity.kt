package com.mask.compose.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mask.compose.R
import com.mask.compose.enums.AnimationType
import com.mask.compose.ui.theme.AndroidCompose_DemoTheme
import com.mask.compose.ui.theme.Dimen
import com.mask.compose.utils.ActivityUtils
import com.mask.compose.utils.LogUtils
import com.mask.compose.utils.ToastUtils

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
        when (selectedTab.value) {
            AnimationType.AnimatedVisibility -> {
                AnimationContentAnimatedVisibility(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            AnimationType.AnimateContentSize -> {
                AnimationContentAnimateContentSize(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            AnimationType.AnimateAsState -> {
                AnimationContentAnimateAsState(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            AnimationType.InfiniteTransition -> {
                AnimationContentInfiniteTransition(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
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
fun AnimationContentAnimatedVisibility(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationContentAnimatedVisibility")

    var isShow by remember { mutableStateOf(true) }
    val fabText = stringResource(R.string.edit)
    val actionText = stringResource(if (isShow) R.string.hide else R.string.show)

    Column(
        modifier = modifier
            .padding(Dimen.padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FloatingActionButton(
            onClick = {
                ToastUtils.show("$fabText $isShow")
            }
        ) {
            Row(
                modifier = Modifier
                    .padding(PaddingValues(16.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Edit, contentDescription = fabText)
                AnimatedVisibility(isShow) {
                    Text(
                        modifier = Modifier
                            .padding(start = Dimen.padding),
                        fontSize = 16.sp,
                        text = fabText
                    )
                }
            }
        }
        Button(
            modifier = Modifier
                .padding(top = Dimen.padding)
                .height(Dimen.buttonHeight),
            onClick = {
                isShow = !isShow
            }
        ) {
            Text(text = actionText)
        }
    }
}

@Composable
fun AnimationContentAnimateContentSize(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationContentAnimateContentSize")

    var isExpand by remember { mutableStateOf(true) }
    val maxLines = if (isExpand) Int.MAX_VALUE else 5
    val text = stringResource(R.string.text_long)
    val actionText = stringResource(if (isExpand) R.string.collapse else R.string.expand)

    Column(
        modifier = modifier
            .padding(Dimen.padding)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan)
                .padding(Dimen.padding)
                .animateContentSize()
                .clickable {
                    isExpand = !isExpand
                },
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            text = text
        )
        Button(
            modifier = Modifier
                .padding(top = Dimen.padding)
                .height(Dimen.buttonHeight),
            onClick = {
                isExpand = !isExpand
            }
        ) {
            Text(text = actionText)
        }
    }
}

@Composable
fun AnimationContentAnimateAsState(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationContentAnimateAsState")

    Text(
        text = "AnimateAsState"
    )
}

@Composable
fun AnimationContentInfiniteTransition(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationContentInfiniteTransition")

    Text(
        text = "InfiniteTransition"
    )
}