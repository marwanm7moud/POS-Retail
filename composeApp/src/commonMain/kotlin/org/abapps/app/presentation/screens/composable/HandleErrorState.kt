package org.abapps.app.presentation.screens.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.StButton
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.resource.Resources
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun HandleErrorState(
    title: String,
    error: ErrorState,
    onError: @Composable () -> Unit = {},
    onClick: () -> Unit = {},
) {
    when (error) {
        is ErrorState.ValidationNetworkError -> NoContent(title)
        is ErrorState.ServerError -> ErrorHappened(title, onClick)
        is ErrorState.NotFound -> NoContent(title)
        is ErrorState.NetworkError -> NoInternet(onClick = onClick)
        else -> onError()
    }
}

@Composable
private fun ErrorPage(
    image: Painter,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    retryButton: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF11142D))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = modifier.fillMaxWidth(),
            painter = image,
            contentDescription = title
        )
        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White.copy(alpha = 0.87f)
        )
        Text(
            modifier = Modifier.padding(
                top = 4.dp,
                bottom = 48.dp
            ),
            text = description,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White.copy(alpha = 0.38f),
            textAlign = TextAlign.Center,
        )
        retryButton()
    }
}

@Composable
private fun NoContent(
    title: String = Resources.strings.nullDataMessage,
    description: String = Resources.strings.nullDataDescription,
) {
    ErrorPage(
        image = painterResource(DrawableResource("drawable/not_found.xml")),
        title = title,
        description = description,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoInternet(onClick: () -> Unit = {}) {
    ErrorPage(
        image = painterResource(DrawableResource("drawable/no_internet.xml")),
        title = Resources.strings.noInternetMessage,
        description = Resources.strings.noInternetDescription,
        retryButton = {
            StButton(
                modifier = Modifier.fillMaxWidth(LocalDensity.current.density / 4f),
                onClick = { onClick() },
                title = Resources.strings.tryAgain,
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ErrorHappened(
    title: String,
    onClick: () -> Unit = {},
) {
    ErrorPage(
        image = painterResource(DrawableResource("drawable/cute.xml")),
        title = title,
        description = Resources.strings.somethingWrongHappened,
        retryButton = {
            StButton(
                modifier = Modifier.fillMaxWidth(LocalDensity.current.density / 4f),
                onClick = { onClick() },
                title = Resources.strings.tryAgain,
            )
        }
    )
}