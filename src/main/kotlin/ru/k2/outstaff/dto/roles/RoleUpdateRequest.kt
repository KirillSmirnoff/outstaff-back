package ru.k2.outstaff.dto.roles

data class RoleUpdateRequest(
        val roleName: String,
        val comment: String?,
)