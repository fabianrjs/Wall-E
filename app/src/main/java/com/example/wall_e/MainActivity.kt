package com.example.wall_e

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.wall_e.ui.theme.WallETheme
import com.example.wall_e.ui.theme.walleColorScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallETheme(darkTheme = true) {
                HomeScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Image(
            painter = rememberAsyncImagePainter(R.drawable.home_backgroud, imageLoader),
            contentDescription = "Background Image Home Screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight,
            alpha = 0.3f
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val promptValue = remember { mutableStateOf("") }

            Text(
                text = stringResource(id = R.string.app_name),
                color = walleColorScheme.primary,
                fontSize = 90.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight(1000)
            )
            TextField(
                value = promptValue.value,
                onValueChange = { newValue ->
                    promptValue.value = newValue
                }
            )
        }
    }
}