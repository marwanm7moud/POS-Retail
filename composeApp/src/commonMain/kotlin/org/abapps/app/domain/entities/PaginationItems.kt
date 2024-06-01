package org.abapps.app.domain.entities

data class PaginationItems<T>(
    val items: List<T>,
    val page: Int,
    val total: Long
)