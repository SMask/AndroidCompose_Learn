package com.mask.compose.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mask.compose.R
import com.mask.compose.ui.ButtonNormal
import com.mask.compose.ui.theme.AndroidCompose_DemoTheme
import com.mask.compose.ui.theme.Dimen
import com.mask.compose.utils.LogUtils

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
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainLayoutPreview() {
    AndroidCompose_DemoTheme {
        MainLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun MainLayout(modifier: Modifier = Modifier) {
    LogUtils.i("MainLayout")

    val context = LocalContext.current

    fun buttonModifier(isTopPadding: Boolean = true) = Modifier
        .padding(top = if (isTopPadding) Dimen.padding else 0.dp)
        .fillMaxWidth()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        ButtonNormal(
            modifier = buttonModifier(false),
            textResId = R.string.title_activity_common_view,
            onClick = {
                CommonViewActivity.startActivity(context)
            }
        )
        ButtonNormal(
            modifier = buttonModifier(),
            textResId = R.string.title_activity_draggable_view,
            onClick = {
                DraggableViewActivity.startActivity(context)
            }
        )
        ButtonNormal(
            modifier = buttonModifier(),
            textResId = R.string.title_activity_state_view_model,
            onClick = {
                StateViewModelActivity.startActivity(context)
            }
        )
        ButtonNormal(
            modifier = buttonModifier(),
            textResId = R.string.title_activity_list,
            onClick = {
                ListActivity.startActivity(context)
            }
        )
        ButtonNormal(
            modifier = buttonModifier(),
            textResId = R.string.title_activity_animation,
            onClick = {
                AnimationActivity.startActivity(context)
            }
        )
    }
}