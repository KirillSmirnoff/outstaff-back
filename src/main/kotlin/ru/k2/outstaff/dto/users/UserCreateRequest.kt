package ru.k2.outstaff.dto.users

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import ru.k2.outstaff.support.UpperCaseDeserializer

data class UserCreateRequest
(
        val userName: String,
        val login: String,
        val password: String,
        val phone: String?,
        val mail: String?,
        @JsonDeserialize(using = UpperCaseDeserializer::class)
        val roles: List<String>
)