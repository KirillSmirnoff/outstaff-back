package ru.k2.outstaff.persistence.dto.roles

data class RoleUpdateRequest(
        val roleName: String,
        val comment: String?,
        val deleted: Int?
)