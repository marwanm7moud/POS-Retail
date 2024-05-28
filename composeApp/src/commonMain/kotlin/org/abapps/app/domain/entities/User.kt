package org.abapps.app.domain.entities

data class User(
    val id: Int,
    val name: String,
    val password: String,
    val userName: String,
    val userClassId: Int,
    val myLanguage: String,
    val title: String,
    val fullName: String,
    val address: String,
    val job: String,
    val phone: String,
    val mobile: String,
    val email: String,
    val passCode: String,
    val active: Boolean,
    val createDate: String,
    val modifiedDate: String,
    val userId: Int,
    val scomId: Int,
    val storeId: Int,
    val sales: Boolean
)