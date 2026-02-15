package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import io.github.alexzhirkevich.qrose.options.QrBallShape
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.QrErrorCorrectionLevel
import io.github.alexzhirkevich.qrose.options.QrFrameShape
import io.github.alexzhirkevich.qrose.options.QrLogoPadding
import io.github.alexzhirkevich.qrose.options.QrPixelShape
import io.github.alexzhirkevich.qrose.options.circle
import io.github.alexzhirkevich.qrose.options.roundCorners
import io.github.alexzhirkevich.qrose.options.solid
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import org.jetbrains.compose.resources.painterResource
import qrgenerator.qrkitpainter.PatternType
import qrgenerator.qrkitpainter.QrBallType
import qrgenerator.qrkitpainter.QrFrameType
import qrgenerator.qrkitpainter.QrKitBrush
import qrgenerator.qrkitpainter.QrKitColors
import qrgenerator.qrkitpainter.QrKitLogo
import qrgenerator.qrkitpainter.QrKitShapes
import qrgenerator.qrkitpainter.QrPixelType
import qrgenerator.qrkitpainter.customBrush
import qrgenerator.qrkitpainter.getSelectedFrameShape
import qrgenerator.qrkitpainter.getSelectedPattern
import qrgenerator.qrkitpainter.getSelectedPixel
import qrgenerator.qrkitpainter.getSelectedQrBall
import qrgenerator.qrkitpainter.rememberQrKitPainter
@Composable
fun QrCode(
    modifier: Modifier,
    data: String,
    myLogo: Painter
){
    val painter = rememberQrKitPainter(data) {
        shapes = QrKitShapes(
            ballShape = getSelectedQrBall(QrBallType.RoundCornersQrBall(radius =100f)),
            darkPixelShape = getSelectedPixel(QrPixelType.RoundCornerPixel(radius = 100f)),
            frameShape = getSelectedFrameShape(QrFrameType.RoundCornersFrame(corner =30f)),
            codeShape = getSelectedPattern(PatternType.SquarePattern),
        )
        colors = QrKitColors(
            darkBrush = QrKitBrush.customBrush {
                Brush.linearGradient(
                    0f to Color.Black,
                    1f to Color.Black,
                    end = Offset(it, it)
                )
            }
        )
        logo= QrKitLogo(myLogo)
    }

    Image(
        painter=painter,
        contentDescription = "qr code for meal",
        modifier = modifier
    )

}