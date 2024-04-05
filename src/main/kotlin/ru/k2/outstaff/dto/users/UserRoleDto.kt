package ru.k2.outstaff.dto.users

data class UserRoleDto(
        var id: Long? = null,
        var userName: String? = null,
        var login: String? = null,
        var password: String? = null,
        var phone: String? = null,
        var mail: String? = null,
        var status: String? = null,
        var roles: List<String>? = null
)