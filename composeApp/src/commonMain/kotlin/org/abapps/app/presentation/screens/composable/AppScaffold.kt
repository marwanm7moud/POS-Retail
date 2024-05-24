package org.abapps.app.presentation.screens.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.beepbeep.designSystem.ui.composable.animate.FadeAnimation
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.resource.Resources
import org.abapps.app.presentation.screens.composable.snackbar.StackedSnackbarHost
import org.abapps.app.presentation.screens.composable.snackbar.StackedSnakbarHostState

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    isLoading: Boolean = false,
    error: ErrorState? = null,
    titleError: String = Resources.strings.error,
    onClickRetry: () -> Unit = {},
//    onRefresh: () -> Unit = {},
//    isRefreshing: Boolean = false,
    onLoading: @Composable () -> Unit = { LoadingScreen() },
    onError: @Composable () -> Unit = {
        HandleErrorState(titleError, error ?: ErrorState.UnknownError(""), {}, onClickRetry)
    },
    stackedSnackbarHostState: StackedSnakbarHostState? = null,
    content: @Composable () -> Unit
) {
//    val pullRefreshState = rememberPullRefreshState(isRefreshing, { onRefresh() })

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = backgroundColor,
        snackbarHost = { stackedSnackbarHostState?.let { StackedSnackbarHost(hostState = it) } },
    ) {
        FadeAnimation(
            visible = isLoading,
        ) {
            onLoading()
        }
        FadeAnimation(
            visible = error != null,
        ) {
            onError()
        }
        FadeAnimation(
            visible = !isLoading,
        ) {
            content()
        }
    }
}