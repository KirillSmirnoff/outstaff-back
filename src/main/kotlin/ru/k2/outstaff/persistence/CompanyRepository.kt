package ru.k2.outstaff.persistence

import org.springframework.data.jpa.repository.JpaRepository
import ru.k2.outstaff.persistence.entity.Company
import java.util.*

interface CompanyRepository : JpaRepository<Company, Long>{

    fun findByCompanyName(companyName: String): Optional<Company>

}