package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import io.github.alexzhirkevich.qrose.options.QrBallShape
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.QrFrameShape
import io.github.alexzhirkevich.qrose.options.QrLogoPadding
import io.github.alexzhirkevich.qrose.options.QrPixelShape
import io.github.alexzhirkevich.qrose.options.circle
import io.github.alexzhirkevich.qrose.options.roundCorners
import io.github.alexzhirkevich.qrose.options.solid
import io.github.alexzhirkevich.qrose.rememberQrCodePainter

@Composable
fun QrCode(
    modifier: Modifier,
    data: String,
    logo: Painter

){
    Image(
        painter = rememberQrCodePainter(data = data,){
            logo {
                painter=logo
                padding= QrLogoPadding.Natural(0.1f)
            }
            shapes {
                ball = QrBallShape.circle()
                darkPixel = QrPixelShape.roundCorners()
                frame = QrFrameShape.roundCorners(.25f)
            }
            colors {
                dark = QrBrush.solid(Color.Black)
                frame = QrBrush.solid(Color.Black)
            }

        },
        contentDescription = "qr code",
        modifier = modifier
    )
}