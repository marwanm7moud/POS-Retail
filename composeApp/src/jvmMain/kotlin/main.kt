import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.abapps.app.di.AppModule
import java.awt.Dimension
import org.abapps.app.presentation.app.App
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin

fun main() = application {
    val state: WindowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        position = WindowPosition(
            Alignment.Center
        )
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "Retail",
        undecorated = false,
        state = state,
        resizable = true,
        icon = painterResource(DrawableResource("drawable/logo.png"))
    ) {
        println(LocalDensity.current.density)
        startKoin {
            modules(AppModule)
            createEagerInstances()
        }
        App()
    }
}