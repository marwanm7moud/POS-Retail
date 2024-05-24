import androidx.compose.ui.window.ComposeUIViewController
import org.abapps.app.presentation.app.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
