package org.abapps.app.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse<T>(
    val data: T?,
    val isSuccess: Boolean,
    val status: ResponseStatusDto,
)

@Serializable
data class ResponseStatusDto(
    val messageError: String? = null,
    val messageSuccess: String? = null,
    val code: Int? = null,
)

@Serializable
data class PaginationResponse<T>(
    @SerialName("items")
    val items: List<T>? = null,
    @SerialName("page")
    val page: Int? = null,
    @SerialName("total")
    val total: Long? = null
)