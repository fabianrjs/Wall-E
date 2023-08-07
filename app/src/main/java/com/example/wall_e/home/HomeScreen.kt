package com.example.wall_e.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wall_e.R
import com.example.wall_e.ui.theme.wallEColorScheme
import com.example.wall_e.utils.getImageLoader
import org.koin.androidx.compose.koinViewModel

const val PROMPT_FIELD_MAX_LINES = 10

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        BackgroundGif()
        PromptForm()
    }
}

@Composable
fun BackgroundGif() {
    Image(
        painter = rememberAsyncImagePainter(
            R.drawable.home_backgroud,
            getImageLoader(LocalContext.current)
        ),
        contentDescription = "Home Screen Background Image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillHeight,
        alpha = 0.3f
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PromptForm() {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val promptValue = remember { mutableStateOf("") }
        val promptEnabled = remember { mutableStateOf(true) }

        Text(
            text = stringResource(id = R.string.app_name),
            color = wallEColorScheme.primary,
            fontSize = 90.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight(1000)
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            value = promptValue.value,
            onValueChange = { newValue ->
                promptValue.value = newValue
            },
            enabled = promptEnabled.value,
            label = { Text(text = "Prompt") }, // TODO strings.xml
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(onSearch = {
                keyboardController?.hide()
                // TODO onSearch
            }),
            maxLines = PROMPT_FIELD_MAX_LINES,
            textStyle = TextStyle(
                fontSize = 20.sp
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                cursorColor = wallEColorScheme.secondary,
                focusedBorderColor = wallEColorScheme.secondary.copy(alpha = 0.6f),
                focusedLabelColor = wallEColorScheme.secondary
            ),
            trailingIcon = {
                if (promptValue.value.isEmpty()) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_microfone),
                            contentDescription = "Record audio button"
                        )
                    }
                }
            }
        )
        Button(
            modifier = Modifier
                .padding(top = 30.dp)
                .height(55.dp)
                .fillMaxWidth(0.8f),
            onClick = { /*TODO*/ },
            enabled = promptEnabled.value,
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = "Generar Wallpapers") // TODO
        }
    }
}