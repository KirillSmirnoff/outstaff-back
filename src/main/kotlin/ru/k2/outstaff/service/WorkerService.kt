package ru.k2.outstaff.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.k2.outstaff.exceptions.WorkerNotFoundException
import ru.k2.outstaff.persistence.CompanyRepository
import ru.k2.outstaff.persistence.WorkerRepository
import ru.k2.outstaff.persistence.dto.worker.WorkerCreateRequest
import ru.k2.outstaff.persistence.dto.worker.WorkerDto
import ru.k2.outstaff.persistence.entity.Worker
import ru.k2.outstaff.utils.WorkerType

@Service
class WorkerService(
        private val workerRepository: WorkerRepository,
        private val companyRepository: CompanyRepository
) {

    fun getWorkersByUserId(userId: Long) {
//        workerRepository.getWorkersByUserId(userId)
    }

    fun getWorker(workerId: Long): WorkerDto {
        return workerRepository.findById(workerId)
                .map { WorkerDto().apply {
                    this.name = it.name
                    this.bithday = it.birthday
                    this.phone = it.phone
                    this.mail = it.mail
                    this.status = it.status
                    this.type = it.type.toString()
                    this.company = it.company
                } }
                .orElseThrow { throw WorkerNotFoundException() }
    }

    @Transactional
    fun createWorker(workerCreateRequest: WorkerCreateRequest) {
        val company = companyRepository.findByName(
                workerCreateRequest.company!!
        ).orElseGet { throw WorkerNotFoundException() }

        val worker = Worker().apply {
            this.name = workerCreateRequest.name
            this.birthday = workerCreateRequest.bithday
            this.phone = workerCreateRequest.phone
            this.mail = workerCreateRequest.mail
            this.type = WorkerType.valueOf(workerCreateRequest.type!!)
            this.company = company
        }

        workerRepository.save(worker)
    }

    fun deleteWorker(workerId: Long) {
        workerRepository.deleteById(workerId)
    }

}