package com.mask.compose.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mask.compose.ui.theme.AndroidCompose_DemoTheme
import com.mask.compose.ui.theme.Dimen
import com.mask.compose.ui.theme.Style
import com.mask.compose.viewmodel.StateViewModel

class StateViewModelActivity : ComponentActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, StateViewModelActivity::class.java)
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
                    StateViewModelLayout(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel()
                    )
                }
            }
        }
    }
}

@Composable
fun StateViewModelLayout(viewModel: StateViewModel, modifier: Modifier = Modifier) {
    val countAdd by viewModel.countAdd.observeAsState(0)
    val countMinus by viewModel.countMinus.observeAsState(0)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimen.padding)
    ) {
        StateViewModelCounter(
            modifier = Modifier
                .fillMaxWidth(),
            content = countAdd.toString(),
            actionText = "加",
            onClick = {
                viewModel.addCount()
            }
        )
        StateViewModelCounter(
            modifier = Modifier
                .padding(top = Dimen.padding)
                .fillMaxWidth(),
            content = countMinus.toString(),
            actionText = "减",
            onClick = {
                viewModel.minusCount()
            }
        )
    }
}

@Composable
fun StateViewModelCounter(
    content: String,
    actionText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color(0x80FF0000))
            .padding(Dimen.padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            style = Style.TextStyle.CONTENT,
            text = content
        )
        Button(
            modifier = Modifier
                .height(Dimen.buttonHeight),
            onClick = onClick
        ) {
            Text(
                text = actionText
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StateViewModelLayoutPreview() {
    AndroidCompose_DemoTheme {
        StateViewModelLayout(
            viewModel = viewModel()
        )
    }
}