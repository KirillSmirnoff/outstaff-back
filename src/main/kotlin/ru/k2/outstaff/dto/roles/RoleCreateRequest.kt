package ru.k2.outstaff.dto.roles

data class RoleCreateRequest
(
        val roleName: String,
        val comment: String?
)