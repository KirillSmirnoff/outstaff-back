package ru.k2.outstaff.service

import org.springframework.stereotype.Service
import ru.k2.outstaff.persistence.CompanyRepository
import ru.k2.outstaff.persistence.WorkerRepository
import ru.k2.outstaff.persistence.dto.WorkerDto
import ru.k2.outstaff.persistence.entity.Worker
import java.time.LocalDate

@Service
class WorkerService(
        private val workerRepository: WorkerRepository,
        private val companyRepository: CompanyRepository
) {

    fun saveWorker(workerDto: WorkerDto) {

        val company = companyRepository.findByName(workerDto.company!!)

        val workerEntity = Worker().apply {
            name = workerDto.name
            birthday = LocalDate.parse(workerDto.bithday)
            phone = workerDto.phone
            mail = workerDto.mail
            status = true
            companyId = company
        }

        workerRepository.saveAndFlush(workerEntity)
    }

    fun getAllWorkers(): MutableList<Worker> = workerRepository.findAll()

}