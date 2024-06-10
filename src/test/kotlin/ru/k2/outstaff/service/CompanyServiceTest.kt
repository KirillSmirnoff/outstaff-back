package ru.k2.outstaff.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import ru.k2.outstaff.AbstractDataBaseContainer
import ru.k2.outstaff.dto.company.CompanyRequest
import ru.k2.outstaff.exceptions.CompanyNotFoundException
import ru.k2.outstaff.repository.CompanyRepository

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
internal class CompanyServiceTest : AbstractDataBaseContainer() {

    companion object {
        lateinit var companyService: CompanyService

        @JvmStatic
        @BeforeAll
        fun init(@Autowired companyRepository: CompanyRepository) {
            companyService = CompanyService(companyRepository)
        }
    }

    @Test
    fun getCompanyById() {
        //Given
        val companyId: Long = 145
        //When
        val company = companyService.getCompanyById(companyId)
        //Then
        assertEquals(companyId, company.id)
    }

    @Test
    fun getAll() {
        //Given
        //When
        assertEquals(2, companyService.getAll().size)
        //Then
    }

    @Test
    fun createCompany() {
        //Given
        val companyName = "TestCompany"
        val companyRequest = CompanyRequest().apply {
            this.companyName = companyName
        }
        val oldSize = companyService.getAll().size
        //When
        val company = companyService.createCompany(companyRequest)
        val newSize = companyService.getAll().size
        //Then
        assertEquals(oldSize.plus(1), newSize)
        assertEquals(companyName, company.companyName)
    }

    @Test
    fun deleteCompany() {
        //Given
        val companyId: Long = 145
        //When
        companyService.deleteCompany(companyId)
        //Then
        assertThrows(CompanyNotFoundException::class.java, { companyService.getCompanyById(companyId) })
    }

    @Test
    fun updateCompany() {
        //Given
        val companyId: Long = 145
        val givenCompany = companyService.getCompanyById(companyId)

        //When
        val updateCompany = companyService.updateCompany(companyId, CompanyRequest().apply {
            companyName = "New Company Name"
            additional = "IT Company"
        })
        //Then
        assertNotEquals(givenCompany.companyName , updateCompany.companyName)
        assertEquals("New Company Name", updateCompany.companyName)
        updateCompany.additional?.let { assertTrue(it.isNotEmpty()) }
    }

}