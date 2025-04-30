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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.mask.compose.R
import com.mask.compose.ui.theme.AndroidCompose_DemoTheme
import com.mask.compose.ui.theme.Dimen
import com.mask.compose.ui.theme.Style
import com.mask.compose.utils.ToastUtils
import com.mask.compose.viewmodel.ListViewModel

class ListActivity : ComponentActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, ListActivity::class.java)
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
                    ListLayout(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel()
                    )
                }
            }
        }
    }
}

@Composable
fun ListLayout(viewModel: ListViewModel, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        ListRoot(
            modifier = Modifier.fillMaxSize(),
            state = listState
        )

        val isVisibleFloatingButton = listState.firstVisibleItemIndex <= 2
        if (isVisibleFloatingButton) {
            FloatingButton(
                modifier = Modifier
                    .padding(Dimen.padding)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
fun FloatingButton(modifier: Modifier = Modifier) {
    FloatingActionButton(
        modifier = modifier,
        shape = CircleShape,
        onClick = {
            ToastUtils.show("FloatingActionButton")
        }
    ) {
        Icon(Icons.Filled.Add, "Add")
    }
}

@Composable
fun ListRoot(state: LazyListState, modifier: Modifier = Modifier) {
    val dataList = (1..100).toMutableList()

    LazyColumn(
        modifier = modifier
            .padding(Dimen.padding),
        verticalArrangement = Arrangement.spacedBy(Dimen.padding),
        state = state
    ) {
        item {
            ListHeader(
                modifier = Modifier.fillMaxWidth()
            )
        }
        itemsIndexed(dataList) { index, data ->
            ListItem(
                modifier = Modifier
                    .fillMaxWidth(),
                content = "Content $index",
                actionText = "Action $data",
                onClick = {
                }
            )
        }
    }
}

@Composable
fun ListItem(
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
            onClick = {
                onClick()
            }
        ) {
            Text(
                text = actionText
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListHeader(modifier: Modifier = Modifier) {
    val url = "https://img2.baidu.com/it/u=1069513219,1854441023&fm=253&f=JPEG"
    val dataList = (1..100).toMutableList()

    Column(
        modifier = modifier
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            contentScale = ContentScale.Crop,
            model = url,
            contentDescription = "GlideImage",
            loading = placeholder(R.color.placeholder_loading),
            failure = placeholder(R.color.placeholder_error)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimen.padding)
                .height(120.dp),
            horizontalArrangement = Arrangement.spacedBy(Dimen.padding)
        ) {
            itemsIndexed(dataList) { index, data ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(9f / 16f)
                        .clip(RoundedCornerShape(Dimen.radius))
                        .background(Color(0x8000FF00))
                        .padding(Dimen.padding)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        style = Style.TextStyle.CONTENT,
                        text = "$index - $data"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListLayoutPreview() {
    AndroidCompose_DemoTheme {
        ListLayout(
            viewModel = viewModel()
        )
    }
}