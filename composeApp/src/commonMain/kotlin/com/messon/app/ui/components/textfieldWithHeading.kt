package com.messon.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font

@Composable
fun TextFieldItem(
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    heading: String,
    headingColor: Color,
    value: String,
    onValueChange: (String) -> Unit,
    textFieldHeight: Dp
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            text = heading,
            color=headingColor,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
            modifier = Modifier.padding(start = 15.dp)
        )

        OutlinedTextField(
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(Res.font.opensansmedium))
            ),
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine, // imp
            maxLines = if (singleLine) 1 else 8,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color.LightGray,
                unfocusedLabelColor = Color.LightGray,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(textFieldHeight)

        )
    }
}
