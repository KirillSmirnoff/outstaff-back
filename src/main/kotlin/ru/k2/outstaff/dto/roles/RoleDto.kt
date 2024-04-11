package ru.k2.outstaff.dto.roles

import java.time.LocalDateTime

data class RoleDto
(
        val roleId: Long,
        val roleName: String,
        val date: LocalDateTime,
        var comment: String? = ""
)