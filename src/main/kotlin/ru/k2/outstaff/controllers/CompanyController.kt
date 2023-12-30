package ru.k2.outstaff.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.k2.outstaff.persistence.dto.company.CompanyRequest
import ru.k2.outstaff.persistence.dto.company.CompanyDto
import ru.k2.outstaff.service.CompanyService

@RestController
@RequestMapping("/company")
class CompanyController(val companyService: CompanyService) {

    @GetMapping("/{companyId}")
    fun getCompany(@PathVariable("companyId") id: Long): CompanyDto {
        return companyService.getCompanyById(id)
    }

    @GetMapping
    fun getCompanies(): List<CompanyDto> {
        return companyService.getAll()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCompany(@RequestBody company: CompanyRequest){
        return companyService.createCompany(company)
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteCompany(@PathVariable("companyId") id: Long){
        return companyService.deleteCompany(id)
    }

    @PostMapping("/{companyId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateCompany(@PathVariable("companyId") id: Long, @RequestBody updateCompany: CompanyRequest){
        return companyService.updateCompany(id, updateCompany)
    }
}