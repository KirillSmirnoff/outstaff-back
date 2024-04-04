package ru.k2.outstaff.persistence.dto.worker

import ru.k2.outstaff.persistence.entity.Company
import java.time.LocalDate

data class WorkerDto
(
        var name: String? = null,
        var bithday: LocalDate? = null,
        var phone: String? = null,
        var mail: String? = null,
        var status: Boolean? = null,
        var type: String? = null,
        var company: Company? = null,
)