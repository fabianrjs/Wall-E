package com.example.wall_e.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val promptValue = homeViewModel.promptValue.collectAsState()
    val promptFieldEnabled = homeViewModel.promptFieldEnabled.collectAsState()
    val executePromptButtonEnabled = homeViewModel.executePromptButtonEnabled.collectAsState()
    val recordAudioButtonEnabled = homeViewModel.recordAudioButtonEnabled.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        BackgroundGif()
        PromptForm(
            promptValue = promptValue,
            promptFieldEnabled = promptFieldEnabled,
            executePromptButtonEnabled = executePromptButtonEnabled,
            onPromptValueChange = homeViewModel::setPromptValue,
            onExecutePromptButtonClick = homeViewModel::executePrompt,
            onRecordAudio = homeViewModel::recordAudio,
            recordAudioButtonEnabled = recordAudioButtonEnabled
        )
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
fun PromptForm(
    promptValue: State<String>,
    promptFieldEnabled: State<Boolean>,
    executePromptButtonEnabled: State<Boolean>,
    recordAudioButtonEnabled: State<Boolean>,
    onPromptValueChange: (String) -> Unit,
    onExecutePromptButtonClick: () -> Unit,
    onRecordAudio: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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
            onValueChange = onPromptValueChange,
            enabled = promptFieldEnabled.value,
            label = {
                Text(text = stringResource(id = R.string.home_screen_prompt_label))
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Go,
            ),
            keyboardActions = KeyboardActions(onGo = {
                keyboardController?.hide()
                onExecutePromptButtonClick()
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
            trailingIcon =
                if (promptValue.value.isEmpty()) {
                    { TrailingIcon(onRecordAudio, recordAudioButtonEnabled) }
                }
                else null
        )

        Button(
            modifier = Modifier
                .padding(top = 30.dp)
                .height(55.dp)
                .fillMaxWidth(0.8f),
            onClick = onExecutePromptButtonClick,
            enabled = executePromptButtonEnabled.value,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = wallEColorScheme.secondary.copy(alpha = 0.6f),
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(id = R.string.home_screen_execute_prompt_button_text),
                fontSize = 18.sp
            )
        }

        ClearPromptButton(
            onClearPromptButtonClick = { onPromptValueChange("") },
            isVisible = promptValue.value.isNotEmpty()
        )
    }
}

@Composable
fun TrailingIcon(
    onRecordAudio: () -> Unit,
    enabled: State<Boolean>
) {
    IconButton(
        modifier = Modifier
            .padding(end = 10.dp)
            .size(30.dp)
            .background(
                color = wallEColorScheme.secondary.copy(alpha = 0.5f),
                shape = RoundedCornerShape(50.dp)
            ),
        onClick = onRecordAudio,
        enabled = enabled.value,
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.icon_microfone),
            contentDescription = "Record audio button",
            tint = Color.White
        )
    }
}

@Composable
fun ClearPromptButton(
    onClearPromptButtonClick: () -> Unit,
    isVisible: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isButtonPressed by interactionSource.collectIsPressedAsState()

    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .height(55.dp)
            .fillMaxWidth(0.8f)
            .background(
                color =
                    if (isButtonPressed) Color.White.copy(alpha = 0.3f)
                    else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClearPromptButtonClick,
                enabled = isVisible
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.home_screen_clear_prompt_text),
            fontSize = 18.sp,
            color = if (isVisible) Color.White else Color.Transparent
        )
    }
}