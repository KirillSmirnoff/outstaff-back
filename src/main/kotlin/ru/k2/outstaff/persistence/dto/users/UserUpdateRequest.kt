package ru.k2.outstaff.persistence.dto.users

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import ru.k2.outstaff.support.UpperCaseDeserializer

data class UserUpdateRequest
(
        val userName: String,
        val phone: String?,
        val mail: String?,
        var deleted: Int?,
        @JsonDeserialize(using = UpperCaseDeserializer::class)
        val roles: ArrayList<String>
)
