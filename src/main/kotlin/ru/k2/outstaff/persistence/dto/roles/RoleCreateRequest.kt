package ru.k2.outstaff.persistence.dto.roles

data class RoleCreateRequest
(
        val roleName: String,
        val comment: String?
)