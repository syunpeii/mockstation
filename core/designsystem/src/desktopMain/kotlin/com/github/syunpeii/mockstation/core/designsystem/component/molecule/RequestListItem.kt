package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.github.syunpeii.mockstation.core.designsystem.component.atom.badge.MethodBadge
import com.github.syunpeii.mockstation.core.designsystem.component.atom.badge.StatusBadge
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import com.github.syunpeii.mockstation.core.model.HttpMethod
import com.github.syunpeii.mockstation.core.model.RequestInfo
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@Composable
fun RequestListItem(
    request: RequestInfo,
    selected: Boolean,
    onClick: () -> Unit,
    timestampLabel: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = if (selected) MockStationTheme.colors.secondaryContainer.copy(alpha = 0.5f) else MockStationTheme.colors.surface,
        shape = MockStationTheme.shapes.small,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MockStationTheme.spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MethodBadge(
                method = request.method,
                modifier = Modifier.padding(end = MockStationTheme.spacing.small),
            )
            Text(
                style = MockStationTheme.typography.bodyMedium,
                text = request.path,
                color = MockStationTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            StatusBadge(
                statusCode = request.statusCode,
            )
            Text(
                style = MockStationTheme.typography.labelSmall,
                text = timestampLabel,
                color = MockStationTheme.colors.onSurfaceVariant,
                modifier = Modifier.padding(start = MockStationTheme.spacing.small),
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun PreviewRequestListItem() {
    MockStationTheme {
        PreviewBox {
            Column(
                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            ) {
                RequestListItem(
                    request = RequestInfo(
                        id = "req-001",
                        method = HttpMethod.GET,
                        path = "/api/user",
                        statusCode = 200,
                        statusText = "OK",
                        timestamp = Clock.System.now().minus(5.minutes),
                        deviceId = "device-001",
                    ),
                    selected = false,
                    onClick = {},
                    timestampLabel = "5m ago",
                )
                RequestListItem(
                    request = RequestInfo(
                        id = "req-002",
                        method = HttpMethod.POST,
                        path = "/api/device/with/very/long/path/that/should/truncate",
                        statusCode = 201,
                        statusText = "Created",
                        timestamp = Clock.System.now().minus(10.minutes),
                        deviceId = "device-001",
                    ),
                    selected = true,
                    onClick = {},
                    timestampLabel = "10m ago",
                )
                RequestListItem(
                    request = RequestInfo(
                        id = "req-003",
                        method = HttpMethod.DELETE,
                        path = "/api/user/123",
                        statusCode = 404,
                        statusText = "Not Found",
                        timestamp = Clock.System.now().minus(15.minutes),
                        deviceId = "device-001",
                    ),
                    selected = false,
                    onClick = {},
                    timestampLabel = "15m ago",
                )
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun PreviewRequestListItemInteractive() {
    MockStationTheme {
        PreviewBox {
            var selectedId by remember { mutableStateOf<String?>(null) }
            Column(
                verticalArrangement = Arrangement.spacedBy(MockStationTheme.spacing.small),
            ) {
                val requests = listOf(
                    RequestInfo(
                        id = "req-001",
                        method = HttpMethod.GET,
                        path = "/api/user",
                        statusCode = 200,
                        statusText = "OK",
                        timestamp = Clock.System.now(),
                        deviceId = "device-001",
                    ),
                    RequestInfo(
                        id = "req-002",
                        method = HttpMethod.PUT,
                        path = "/api/settings",
                        statusCode = 500,
                        statusText = "Internal Server Error",
                        timestamp = Clock.System.now(),
                        deviceId = "device-001",
                    ),
                )
                requests.forEach { request ->
                    RequestListItem(
                        request = request,
                        selected = selectedId == request.id,
                        onClick = {
                            selectedId = if (selectedId == request.id) null else request.id
                        },
                        timestampLabel = "Just now",
                    )
                }
            }
        }
    }
}
