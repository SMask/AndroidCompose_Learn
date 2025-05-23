package com.mask.compose.activity

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
import com.mask.compose.R
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
                        modifier = Modifier.padding(innerPadding)
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
        MainLayout()
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
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ActivityButton(
            modifier = buttonModifier(false),
            textResId = R.string.title_activity_common_view,
            onClick = {
                CommonViewActivity.startActivity(context)
            }
        )
        ActivityButton(
            modifier = buttonModifier(),
            textResId = R.string.title_activity_draggable_view,
            onClick = {
                DraggableViewActivity.startActivity(context)
            }
        )
        ActivityButton(
            modifier = buttonModifier(),
            textResId = R.string.title_activity_state_view_model,
            onClick = {
                StateViewModelActivity.startActivity(context)
            }
        )
        ActivityButton(
            modifier = buttonModifier(),
            textResId = R.string.title_activity_list,
            onClick = {
                ListActivity.startActivity(context)
            }
        )
        ActivityButton(
            modifier = buttonModifier(),
            textResId = R.string.title_activity_animation,
            onClick = {
                AnimationActivity.startActivity(context)
            }
        )
    }
}

@Composable
fun ActivityButton(textResId: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    LogUtils.i("ActivityButton ${stringResource(textResId)}")

    Button(
        modifier = modifier
            .height(Dimen.buttonHeight),
        onClick = onClick
    ) {
        Text(
            text = stringResource(textResId)
        )
    }
}