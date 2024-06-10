package ru.k2.outstaff.controllers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import ru.k2.outstaff.dto.company.CompanyDto
import ru.k2.outstaff.repository.entity.Company

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class CompanyControllerTest {

    @Test
    fun getCompany(@Autowired restTemplate: TestRestTemplate) {
        // given
        val companyId: Long = 114
        // when
        val company = restTemplate.getForEntity("/company/$companyId", CompanyDto::class.java)

        //then
        assertEquals(HttpStatus.OK, company.statusCode)
        assertEquals(companyId, company.body?.id)
    }
}