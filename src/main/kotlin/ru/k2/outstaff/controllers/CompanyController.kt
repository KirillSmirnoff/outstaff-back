package ru.k2.outstaff.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.k2.outstaff.dto.company.CompanyRequest
import ru.k2.outstaff.dto.company.CompanyDto
import ru.k2.outstaff.service.CompanyService

@RestController
@RequestMapping("/company")
class CompanyController(val companyService: CompanyService) {

    @GetMapping("/{companyId}")
    fun getCompany(@PathVariable("companyId") id: Long): ResponseEntity<CompanyDto> {
        val company = companyService.getCompanyById(id)
        return ResponseEntity.ok(company)
    }

    @GetMapping
    fun listCompanies(): ResponseEntity<List<CompanyDto>> {
        val companies = companyService.getAll()
        return ResponseEntity.ok(companies)
    }

    @PostMapping
    fun createCompany(@RequestBody company: CompanyRequest): ResponseEntity<CompanyDto>{
        val newCompany = companyService.createCompany(company)
        return ResponseEntity(newCompany, HttpStatus.CREATED)
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteCompany(@PathVariable("companyId") id: Long){
        companyService.deleteCompany(id)
    }

    @PostMapping("/{companyId}")
    fun updateCompany(@PathVariable("companyId") id: Long,
                      @RequestBody updateCompany: CompanyRequest): ResponseEntity<CompanyDto>{
        val company = companyService.updateCompany(id, updateCompany)
        return ResponseEntity.ok(company)
    }
}