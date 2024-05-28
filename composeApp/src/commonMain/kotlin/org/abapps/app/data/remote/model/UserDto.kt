package org.abapps.app.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int? = null,
    val name: String? = null,
    val password: String? = null,
    val userName: String? = null,
    val userClassId: Int? = null,
    val myLanguage: String? = null,
    val title: String? = null,
    val fullName: String? = null,
    val address: String? = null,
    val job: String? = null,
    val phone: String? = null,
    val mobile: String? = null,
    val email: String? = null,
    val passCode: String? = null,
    val active: Boolean? = null,
    val createDate: String? = null,
    val modifiedDate: String? = null,
    val userId: Int? = null,
    val scomId: Int? = null,
    val storeId: Int? = null,
    val sales: Boolean? = null
)