package com.mask.compose.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mask.compose.ui.theme.AndroidCompose_DemoTheme
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
    Text(
        modifier = modifier,
        text = "Hello"
    )
}