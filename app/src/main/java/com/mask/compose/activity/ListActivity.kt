package com.mask.compose.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.mask.compose.R
import com.mask.compose.common.config.Global
import com.mask.compose.ui.theme.AndroidCompose_DemoTheme
import com.mask.compose.ui.theme.Dimen
import com.mask.compose.ui.theme.Style
import com.mask.compose.utils.ActivityUtils
import com.mask.compose.viewmodel.ListViewModel
import com.mask.compose.vo.ListItemVo

class ListActivity : ComponentActivity() {

    companion object {
        fun startActivity(context: Context) {
            ActivityUtils.startActivity(context, ListActivity::class.java)
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

@Preview(showBackground = true)
@Composable
fun ListLayoutPreview() {
    AndroidCompose_DemoTheme {
        ListLayout(
            viewModel = viewModel()
        )
    }
}

@Composable
fun ListLayout(viewModel: ListViewModel, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val isVisibleFloatingButton by remember { derivedStateOf { listState.firstVisibleItemIndex <= 0 } }
    val totalPrice by viewModel.totalPrice.observeAsState(0)

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ListRoot(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                viewModel = viewModel,
                state = listState
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color(0x80CCCCCC))
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = Dimen.padding),
                    style = Style.TextStyle.CONTENT,
                    text = stringResource(R.string.list_total_price, totalPrice)
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .padding(Dimen.padding)
                .align(Alignment.BottomEnd),
            visible = isVisibleFloatingButton,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            FloatingButton(
                onClick = {
                    viewModel.addItem()
                }
            )
        }
    }
}

@Composable
fun FloatingButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        modifier = modifier,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
        onClick = onClick
    ) {
        Icon(Icons.Filled.Add, stringResource(R.string.add))
    }
}

@Composable
fun ListRoot(state: LazyListState, viewModel: ListViewModel, modifier: Modifier = Modifier) {
    val dataList by viewModel.itemList.observeAsState(emptyList())

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
        itemsIndexed(
            items = dataList ?: emptyList(),
            key = { _, data ->
                data?.id ?: ""
            }
        ) { _, data ->
            if (data != null) {
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    data = data,
                    viewModel = viewModel
                )
            }
        }
        item {
            ListFooter(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ListItem(data: ListItemVo, viewModel: ListViewModel, modifier: Modifier = Modifier) {
    var isExpand by remember { mutableStateOf(true) }

    Row(
        modifier = modifier
            .background(Color(0x80FF0000))
            .padding(Dimen.padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .animateContentSize(),
                style = Style.TextStyle.CONTENT,
                maxLines = if (isExpand) 10 else 2,
                overflow = TextOverflow.Ellipsis,
                text = stringResource(
                    R.string.list_item_content,
                    data.id ?: "",
                    data.name ?: "",
                    data.price ?: 0,
                    data.quantity ?: 0,
                    data.totalPrice
                )
            )
            Button(
                modifier = Modifier
                    .padding(top = Dimen.padding)
                    .height(Dimen.buttonHeight),
                onClick = {
                    isExpand = !isExpand
                }
            ) {
                Text(text = stringResource(if (isExpand) R.string.collapse else R.string.expand))
            }
        }
        IconButton(
            onClick = {
                viewModel.deleteItem(data.id)
            }
        ) {
            Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
        }
        IconButton(
            onClick = {
                viewModel.minusQuantity(data.id)
            }
        ) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = stringResource(R.string.minus))
        }
        IconButton(
            onClick = {
                viewModel.addQuantity(data.id)
            }
        ) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = stringResource(R.string.add))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListHeader(modifier: Modifier = Modifier) {
    val dataList = (1..30).toMutableList()

    Column(
        modifier = modifier
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(Dimen.radius)),
            contentScale = ContentScale.Crop,
            model = Global.Url.Image.BX_1,
            contentDescription = null,
            loading = placeholder(R.color.placeholder_loading),
            failure = placeholder(R.color.placeholder_error)
        )
        LazyRow(
            modifier = Modifier
                .padding(top = Dimen.padding)
                .fillMaxWidth()
                .height(120.dp),
            horizontalArrangement = Arrangement.spacedBy(Dimen.padding)
        ) {
            itemsIndexed(
                items = dataList,
                key = { _, data ->
                    data
                }
            ) { index, data ->
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
                        text = stringResource(R.string.list_header_item_content, index, data)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListFooter(modifier: Modifier = Modifier) {
    val dataList = (1..30).toMutableList()

    Column(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            verticalArrangement = Arrangement.spacedBy(Dimen.padding)
        ) {
            itemsIndexed(
                items = dataList,
                key = { _, data ->
                    data
                }
            ) { index, data ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 1f)
                        .clip(RoundedCornerShape(Dimen.radius))
                        .background(Color(0x800000FF))
                        .padding(Dimen.padding)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        style = Style.TextStyle.CONTENT,
                        text = stringResource(R.string.list_footer_item_content, index, data)
                    )
                }
            }
        }
        GlideImage(
            modifier = Modifier
                .padding(top = Dimen.padding)
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(Dimen.radius)),
            contentScale = ContentScale.Crop,
            model = Global.Url.Image.BX_1,
            contentDescription = null,
            loading = placeholder(R.color.placeholder_loading),
            failure = placeholder(R.color.placeholder_error)
        )
    }
}