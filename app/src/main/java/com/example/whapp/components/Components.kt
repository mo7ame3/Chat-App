package com.example.whapp.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.whapp.R
import com.example.whapp.util.CommonProgressSpinner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(value: MutableState<TextFieldValue>, label: String) {
    OutlinedTextField(value = value.value,
        onValueChange = { value.value = it },
        modifier = Modifier.padding(8.dp),
        label = {
            Text(
                text = label
            )
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(value: MutableState<TextFieldValue>, eye: MutableState<Boolean>) {
    val visualTransformation =
        if (eye.value) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(value = value.value,
        onValueChange = { value.value = it },
        modifier = Modifier.padding(8.dp),
        label = {
            Text(
                text = "Password"
            )
        },
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(onClick = { eye.value = !eye.value }) {
                if (eye.value) Icon(
                    painter = painterResource(id = R.drawable.visibilityoff),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                else Icon(
                    painter = painterResource(id = R.drawable.visibility),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        })
}


@Composable
fun ChatLogo() {
    Image(
        painter = painterResource(id = R.drawable.chat),
        contentDescription = null,
        modifier = Modifier
            .width(200.dp)
            .padding(top = 16.dp)
            .padding(8.dp)
    )
}

@Composable
fun ChatLabel(label: String) {
    Text(
        text = label,
        modifier = Modifier.padding(8.dp),
        fontSize = 30.sp,
        fontFamily = FontFamily.SansSerif
    )
}

@Composable
fun ChatButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick.invoke()
        }, modifier = Modifier.padding(8.dp)
    ) {
        Text(text = label)
    }
}

@Composable
fun NavText(label: String, onClick: () -> Unit) {

    Text(text = label, color = Color.Blue, modifier = Modifier
        .padding(8.dp)
        .clickable {
            onClick.invoke()
        })
}

@Composable
fun CommonDivider() {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.3f)
            .padding(top = 8.dp, bottom = 8.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileChangeDetails(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = label, modifier = Modifier.width(100.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Blue, containerColor = Color.Transparent
            )
        )

    }
}


@Composable
fun CommonImage(
    imageUrl: String?,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale = ContentScale.Crop
) {
    val painter = rememberAsyncImagePainter(model = imageUrl)

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
    if (painter.state is AsyncImagePainter.State.Loading) {
        CommonProgressSpinner()
    }
}