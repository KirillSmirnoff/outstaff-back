package ru.k2.outstaff.dto.worker

import java.time.LocalDate

data class WorkerCreateRequest(
        var name: String? = null,
        var bithday: LocalDate? = null,
        var phone: String? = null,
        var mail: String? = null,
        var type: String? = null,
        var company: String? = null
)