package org.abapps.app.presentation.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.presentation.screens.home.HomeScreen
import org.abapps.app.resource.StarTouchTheme
import org.abapps.app.util.getScreenModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun App() {
    MainApp.Content()
}

object MainApp : Screen {
    @Composable
    override fun Content() {
        val appScreenModel = getScreenModel<AppScreenModel>()
        val userLanguage by appScreenModel.state.collectAsState()
        StarTouchTheme(languageCode = userLanguage) {
            Box(
                Modifier.fillMaxSize()
            ) {
                Image(
                    modifier = Modifier.fillMaxSize().background(Theme.colors.background),
                    painter = painterResource(DrawableResource("drawable/background_pattern.png")),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Navigator(HomeScreen()) {
                    SlideTransition(it)
                }
            }
        }
    }
}