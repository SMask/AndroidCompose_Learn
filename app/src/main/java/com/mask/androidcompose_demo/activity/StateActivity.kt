package com.mask.androidcompose_demo.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.mask.androidcompose_demo.ui.theme.AndroidCompose_DemoTheme

class StateActivity : ComponentActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, StateActivity::class.java)
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
                    StateLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun StateLayout(modifier: Modifier = Modifier) {
    Text(
        text = "Hello",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun StateLayoutPreview() {
    AndroidCompose_DemoTheme {
        StateLayout()
    }
}