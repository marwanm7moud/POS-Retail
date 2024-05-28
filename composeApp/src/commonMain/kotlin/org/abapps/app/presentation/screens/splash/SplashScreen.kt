package org.abapps.app.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import org.abapps.app.presentation.screens.home.HomeScreen
import org.abapps.app.resource.Resources
import org.jetbrains.compose.resources.painterResource
import pos_retail.composeapp.generated.resources.Res
import pos_retail.composeapp.generated.resources.ic_cyclone

object SplashScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            delay(1000)
            navigator.replace(HomeScreen())
        }

        Surface(
            Modifier.fillMaxSize(),
            color = if (isSystemInDarkTheme()) Color.Black else Color.White
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(Res.drawable.ic_cyclone),
                    contentDescription = Resources.strings.logo,
                    modifier = Modifier.fillMaxSize(0.4f),
                )
            }
        }
    }
}