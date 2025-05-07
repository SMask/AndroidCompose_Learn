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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mask.compose.enums.AnimationType
import com.mask.compose.ui.theme.AndroidCompose_DemoTheme
import com.mask.compose.ui.theme.Dimen
import com.mask.compose.utils.ActivityUtils

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
    var tabSelectedIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AnimationTab(
            modifier = Modifier
                .fillMaxWidth(),
            onTabClick = { index ->
                tabSelectedIndex = index
            })
    }
}

@Composable
fun AnimationTab(onTabClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    var tabSelectedIndex by remember { mutableIntStateOf(0) }

    LazyRow(
        modifier = modifier
            .background(Color(0x80CCCCCC))
            .padding(Dimen.padding),
        horizontalArrangement = Arrangement.spacedBy(Dimen.padding)
    ) {
        itemsIndexed(AnimationType.entries) { index, data ->
            val isSelected by remember { derivedStateOf { tabSelectedIndex == index } }
            val btnBgColor = if (isSelected) Color.Black else Color(0xFFF7F7F7)
            val textColor = if (isSelected) Color.White else Color.Black
            Button(
                modifier = Modifier
                    .height(Dimen.buttonHeight),
                colors = ButtonDefaults.buttonColors(containerColor = btnBgColor),
                onClick = {
                    tabSelectedIndex = index
                    onTabClick(index)
                }
            ) {
                Text(
                    color = textColor,
                    text = stringResource(data.textResId)
                )
            }
        }
    }
}