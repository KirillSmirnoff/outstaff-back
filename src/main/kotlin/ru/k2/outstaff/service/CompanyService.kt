package ru.k2.outstaff.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.k2.outstaff.exceptions.CompanyNotFoundException
import ru.k2.outstaff.persistence.CompanyRepository
import ru.k2.outstaff.dto.company.CompanyRequest
import ru.k2.outstaff.dto.company.CompanyDto
import ru.k2.outstaff.persistence.entity.Company
import java.util.stream.Collectors

@Service
class CompanyService(val companyRepository: CompanyRepository) {


    fun getCompanyById(id: Long): CompanyDto {
        return companyRepository.findById(id).map {
            CompanyDto().apply {
                this.id = it.id
                this.companyName = it.companyName
                this.additional = it.additional
            }
        }.orElseGet { throw CompanyNotFoundException() }
    }

    fun getAll(): List<CompanyDto> {
        return companyRepository.findAll()
                .stream()
                .map {
                    CompanyDto().apply {
                        this.id = it.id
                        this.companyName = it.companyName
                        this.additional = it.additional
                    }
                }
                .collect(Collectors.toList())
                .orEmpty()
    }

    @Transactional
    fun createCompany(company: CompanyRequest): CompanyDto {
        return mapCompanyDto(
                companyRepository.save(Company().apply {
                    this.companyName = company.companyName
                    this.additional = company.additional
                })
        )
    }

    fun deleteCompany(id: Long) {
        companyRepository.deleteById(id)
    }

    @Transactional
    fun updateCompany(id: Long, updateCompany: CompanyRequest): CompanyDto {
        val company = companyRepository.getById(id)
        company.companyName = updateCompany.companyName
        company.additional = updateCompany.additional

       return mapCompanyDto(companyRepository.save(company))
    }

    fun mapCompanyDto(company: Company): CompanyDto {
        return CompanyDto().apply {
            this.id = company.id
            this.companyName = company.companyName
            this.additional = company.additional
        }
    }
}