package com.mask.compose.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mask.compose.R
import com.mask.compose.enums.AnimationType
import com.mask.compose.ui.theme.AndroidCompose_DemoTheme
import com.mask.compose.ui.theme.ColorArr
import com.mask.compose.ui.theme.Dimen
import com.mask.compose.ui.theme.Style
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
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
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
        AnimationLayout(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun AnimationLayout(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationLayout")

    val selectedTab = remember { mutableStateOf(AnimationType.AnimatedVisibility) }
    val selectedTabPre = remember { mutableStateOf(selectedTab.value) }
    val isSwitchFromLeftToRight by remember { derivedStateOf { selectedTab.value.ordinal > selectedTabPre.value.ordinal } }

    LogUtils.i("AnimationLayout selectedTab: ${selectedTab.value} selectedTabPre: ${selectedTabPre.value} isSwitchFromLeftToRight: $isSwitchFromLeftToRight")

    val anim = (slideInHorizontally(
        initialOffsetX = { fullWidth ->
            if (isSwitchFromLeftToRight) {
                fullWidth
            } else {
                -fullWidth
            }
        }
    )).togetherWith(
        slideOutHorizontally(
            targetOffsetX = { fullWidth ->
                if (isSwitchFromLeftToRight) {
                    -fullWidth
                } else {
                    fullWidth
                }
            }
        ))

    Column(
        modifier = modifier
    ) {
        AnimationTab(
            modifier = Modifier
                .fillMaxWidth(),
            selectedTab = selectedTab,
            onClick = { data ->
                selectedTabPre.value = selectedTab.value
                selectedTab.value = data
            }
        )
        AnimatedContent(
            targetState = selectedTab.value,
            transitionSpec = {
                anim
            }
        ) { data ->
            when (data) {
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
}

@Composable
fun AnimationTab(selectedTab: MutableState<AnimationType>, onClick: (AnimationType) -> Unit, modifier: Modifier = Modifier) {
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
                    onClick(data)
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

    Column(
        modifier = modifier
            .padding(Dimen.padding),
        verticalArrangement = Arrangement.Center
    ) {
        AnimationContentAnimateFloatAsState(
            modifier = Modifier
                .fillMaxWidth()
        )
        AnimationContentAnimateColorAsState(
            modifier = Modifier
                .padding(top = Dimen.paddingItem)
                .fillMaxWidth()
        )
    }
}

@Composable
fun AnimationContentAnimateFloatAsState(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationContentAnimateFloatAsState")

    var progress by remember { mutableFloatStateOf(0.2f) }
    val animProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        modifier = modifier
    ) {
        Text(
            style = Style.TextStyle.LABEL,
            text = stringResource(R.string.animation_animate_float_as_state)
        )
        LinearProgressIndicator(
            modifier = Modifier
                .padding(top = Dimen.padding)
                .fillMaxWidth()
                .height(6.dp),
            color = Color(0x80FF0000),
            strokeCap = StrokeCap.Round,
            progress = {
                animProgress
            }
        )
        Button(
            modifier = Modifier
                .padding(top = Dimen.padding)
                .height(Dimen.buttonHeight)
                .align(Alignment.End),
            onClick = {
                if (progress >= 1.0f) {
                    progress = 0f
                } else {
                    progress += 0.2f
                }
            }
        ) {
            Text(text = stringResource(R.string.update))
        }
    }
}

@Composable
fun AnimationContentAnimateColorAsState(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationContentAnimateColorAsState")

    var index by remember { mutableIntStateOf(0) }
    val color = ColorArr[index]
    val animColor by animateColorAsState(targetValue = color)

    Column(
        modifier = modifier,
    ) {
        Text(
            style = Style.TextStyle.LABEL,
            text = stringResource(R.string.animation_animate_color_as_state)
        )
        Box(
            modifier = Modifier
                .padding(top = Dimen.padding)
                .fillMaxWidth()
                .height(120.dp)
                .background(animColor)
        )
        Button(
            modifier = Modifier
                .padding(top = Dimen.padding)
                .height(Dimen.buttonHeight)
                .align(Alignment.End),
            onClick = {
                if (index >= (ColorArr.size - 1)) {
                    index = 0
                } else {
                    index++
                }
            }
        ) {
            Text(text = stringResource(R.string.update))
        }
    }
}

@Composable
fun AnimationContentInfiniteTransition(modifier: Modifier = Modifier) {
    LogUtils.i("AnimationContentInfiniteTransition")

    val infiniteTransition = rememberInfiniteTransition()
    val animAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = modifier
    ) {
        AnimationContentSkeletonLoading(
            modifier = Modifier
                .alpha(animAlpha)
                .fillMaxWidth()
        )
    }
}

@Composable
fun AnimationContentSkeletonLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(Dimen.padding),
        verticalArrangement = Arrangement.spacedBy(Dimen.padding)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .background(Color.LightGray)
        )
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(Dimen.padding)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(Dimen.padding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(Dimen.radius))
                        .background(Color.LightGray)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(Dimen.radius))
                        .background(Color.LightGray)
                )
            }
        }
    }
}