package ru.k2.outstaff.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.k2.outstaff.repository.entity.Company
import java.util.*

interface CompanyRepository : JpaRepository<Company, Long>{

    fun findByCompanyName(companyName: String): Optional<Company>

}