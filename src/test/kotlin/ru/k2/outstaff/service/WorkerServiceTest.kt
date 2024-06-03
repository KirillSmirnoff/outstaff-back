package ru.k2.outstaff.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.test.context.ActiveProfiles
import ru.k2.outstaff.AbstractDataBaseContainer
import ru.k2.outstaff.dto.worker.WorkerCreateRequest
import ru.k2.outstaff.repository.CompanyRepository
import ru.k2.outstaff.repository.WorkerRepository
import ru.k2.outstaff.utils.WorkerType
import java.time.LocalDate

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
internal class WorkerServiceTest: AbstractDataBaseContainer() {

    companion object {
        lateinit var service: WorkerService

        @JvmStatic
        @BeforeAll
        fun init(@Autowired workerRepository: WorkerRepository,
                 @Autowired companyRepository: CompanyRepository) {
            service = WorkerService(workerRepository, companyRepository)
        }
    }

    @Test
    fun getWorkers() {
        //Given
        //When
        val workers = service.getWorkers()
        //Then
        assertTrue(workers.isNotEmpty())
    }

    @Test
    fun getWorker() {
        //Given
        val workerId: Long = 134
        //When
        //Then
        assertDoesNotThrow { service.getWorker(workerId) }
    }

    @Test
    fun createWorker() {
        //Given
        val worker = WorkerCreateRequest().apply {
            name = "Bahtygul"
            bithday = LocalDate.of(1978, 10, 23)
            phone = "+47982776"
            mail = "gorshok@mail.com"
            type = WorkerType.CITIZEN.name
            company = "OOO TODO"
        }
        val sizeBefore = service.getWorkers().size
        //When
        val savedWorker = service.createWorker(worker)
        //Then
        assertEquals(sizeBefore+1, service.getWorkers().size)
        assertEquals(worker.name, savedWorker.name)
    }

    @Test
    fun deleteWorker() {
        //Given
        val workerId: Long = 134
        //When
        service.deleteWorker(workerId)
        //Then
        assertThrows(EmptyResultDataAccessException::class.java) { service.deleteWorker(workerId) }
    }

    @Test
    fun updateWorker() {
        //Given
        val workerId: Long = 134
        val originWorker = service.getWorker(workerId)
        val worker = WorkerCreateRequest().apply {
            name = "Bahtygul"
            bithday = LocalDate.of(1978, 10, 23)
            phone = "+47982776"
            mail = "gorshok@mail.com"
            type = WorkerType.CITIZEN.name
            company = "OOO TODO"
        }
        //When
        val updateWorker = service.updateWorker(workerId, worker)
        //Then
        assertNotEquals(originWorker.name, updateWorker.name)
        assertEquals(worker.company, updateWorker.company)

    }

}