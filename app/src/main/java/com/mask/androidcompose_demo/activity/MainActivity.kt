package com.mask.androidcompose_demo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mask.androidcompose_demo.R
import com.mask.androidcompose_demo.ui.theme.AndroidCompose_DemoTheme
import com.mask.androidcompose_demo.ui.theme.Dimen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidCompose_DemoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainLayout(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimen.buttonHeight),
            onClick = {
                CommonViewActivity.startActivity(context)
            }) {
            Text(
                text = stringResource(R.string.title_activity_common_view)
            )
        }
        Button(
            modifier = Modifier
                .padding(top = Dimen.padding)
                .fillMaxWidth()
                .height(Dimen.buttonHeight),
            onClick = {
                DraggableViewActivity.startActivity(context)
            }) {
            Text(
                text = stringResource(R.string.title_activity_draggable_view)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainLayoutPreview() {
    AndroidCompose_DemoTheme {
        MainLayout()
    }
}