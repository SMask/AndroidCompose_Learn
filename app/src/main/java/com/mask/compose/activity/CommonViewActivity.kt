package com.mask.compose.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.mask.compose.R
import com.mask.compose.common.config.Global
import com.mask.compose.ui.theme.AndroidCompose_DemoTheme
import com.mask.compose.ui.theme.Dimen
import com.mask.compose.ui.theme.Style
import com.mask.compose.utils.LogUtils

class CommonViewActivity : ComponentActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, CommonViewActivity::class.java)
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
                    CommonViewLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommonViewLayoutPreview() {
    AndroidCompose_DemoTheme {
        CommonViewLayout()
    }
}

@Composable
fun CommonViewLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CommonButton()
        CommonTextField()
        CommonImageDrawable()
        CommonImageBitmap()
        CommonImageGlide()
        CommonImageRound()
        CommonImageCircle(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        CommonProgress()
    }
}

@Composable
private fun CommonButton() {
    val context = LocalContext.current
    val btnText = stringResource(R.string.common_view_button)

    Text(
        style = Style.TextStyle.LABEL,
        text = btnText
    )
    Button(
        modifier = Modifier
            .padding(top = Dimen.padding)
            .fillMaxWidth()
            .height(Dimen.buttonHeight),
        contentPadding = PaddingValues(0.dp),
        onClick = {
            Toast.makeText(context, btnText, Toast.LENGTH_LONG).show()
        }
    ) {
        Text(
            text = btnText
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimen.paddingItem)
    )
}

@Composable
private fun CommonTextField() {
    var content by rememberSaveable { mutableStateOf("") }
    val tfText = stringResource(R.string.common_view_text_field)

    Text(
        style = Style.TextStyle.LABEL,
        text = tfText
    )
    TextField(
        modifier = Modifier
            .padding(top = Dimen.padding)
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0x80FF0000),
            unfocusedContainerColor = Color(0x8000FF00),
            disabledContainerColor = Color(0x800000FF),
            errorContainerColor = Color.Cyan
        ),
        onValueChange = { value ->
            LogUtils.i(value)
            content = value
        },
        value = content,
        placeholder = {
            Text(
                color = Color.Gray,
                text = stringResource(R.string.common_view_text_field_hint, tfText)
            )
        }
    )

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimen.paddingItem)
    )
}

@Composable
private fun CommonImageDrawable() {
    val imgDrawableText = stringResource(R.string.common_view_image_drawable)

    Text(
        style = Style.TextStyle.LABEL,
        text = imgDrawableText
    )
    Box(
        modifier = Modifier
            .padding(top = Dimen.padding)
            .fillMaxWidth()
            .height(120.dp)
            .background(Color(0x80FF0000))
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.image_bx),
            contentDescription = imgDrawableText
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimen.paddingItem)
    )
}

@Composable
private fun CommonImageBitmap() {
    val imgBitmapText = stringResource(R.string.common_view_image_bitmap)
    val bitmap = ImageBitmap.imageResource(R.drawable.image_bx)

    Text(
        style = Style.TextStyle.LABEL,
        text = imgBitmapText
    )
    Box(
        modifier = Modifier
            .padding(top = Dimen.padding)
            .fillMaxWidth()
            .height(120.dp)
            .background(Color(0x80FF0000))
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = bitmap,
            contentDescription = imgBitmapText
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimen.paddingItem)
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CommonImageGlide() {
    val imgGlideText = stringResource(R.string.common_view_image_glide)

    Text(
        style = Style.TextStyle.LABEL,
        text = imgGlideText
    )
    Box(
        modifier = Modifier
            .padding(top = Dimen.padding)
            .fillMaxWidth()
            .height(120.dp)
            .background(Color(0x80FF0000))
    ) {
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            model = Global.Url.Image.BX_1,
            contentDescription = imgGlideText,
            loading = placeholder(R.color.placeholder_loading),
            failure = placeholder(R.color.placeholder_error)
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimen.paddingItem)
    )
}

@Composable
private fun CommonImageRound() {
    val imgRoundText = stringResource(R.string.common_view_image_round)

    Text(
        style = Style.TextStyle.LABEL,
        text = imgRoundText
    )
    Box(
        modifier = Modifier
            .padding(top = Dimen.padding)
            .fillMaxWidth()
            .height(120.dp)
            .background(Color(0x80FF0000))
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(Dimen.radius)),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.image_bx),
            contentDescription = imgRoundText
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimen.paddingItem)
    )
}

@Composable
private fun CommonImageCircle(modifier: Modifier) {
    val imgCircleText = stringResource(R.string.common_view_image_circle)

    Text(
        style = Style.TextStyle.LABEL,
        text = imgCircleText
    )
    Box(
        modifier = modifier
            .padding(top = Dimen.padding)
            .size(120.dp)
            .background(Color(0x80FF0000))
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.image_bx),
            contentDescription = imgCircleText
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimen.paddingItem)
    )
}

@Composable
private fun CommonProgress() {
    val progressText = stringResource(R.string.common_view_progress)

    Text(
        style = Style.TextStyle.LABEL,
        text = progressText
    )
    CircularProgressIndicator(
        modifier = Modifier
            .padding(top = Dimen.padding)
            .size(80.dp),
        color = Color(0x80FF0000),
        trackColor = Color(0xFFF0F0F0),
        strokeWidth = 6.dp,
        strokeCap = StrokeCap.Round
    )
    LinearProgressIndicator(
        modifier = Modifier
            .padding(top = Dimen.padding)
            .fillMaxWidth()
            .height(6.dp),
        color = Color(0x80FF0000),
        trackColor = Color(0xFFF0F0F0),
        strokeCap = StrokeCap.Round
    )
}