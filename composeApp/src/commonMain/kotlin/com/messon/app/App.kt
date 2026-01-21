package com.messon.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.messon.app.ui.components.QrCode
import com.messon.app.ui.screens.mainscreen.HomeScreen
import com.messon.app.ui.screens.mainscreen.MainScreen
import org.jetbrains.compose.resources.painterResource

import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.compose_multiplatform
import messon.composeapp.generated.resources.person

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainScreen()
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
//            QrCode(
//                modifier = Modifier.size(100.dp),
//                data = "sundram",
//                logo = painterResource(Res.drawable.person)
//            )
//        }
    }
}