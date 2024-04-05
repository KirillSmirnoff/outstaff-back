package ru.k2.outstaff.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.k2.outstaff.exceptions.WorkerNotFoundException
import ru.k2.outstaff.persistence.CompanyRepository
import ru.k2.outstaff.persistence.WorkerRepository
import ru.k2.outstaff.dto.worker.WorkerCreateRequest
import ru.k2.outstaff.dto.worker.WorkerDto
import ru.k2.outstaff.persistence.entity.Worker
import ru.k2.outstaff.utils.WorkerType
import java.util.stream.Collectors

@Service
class WorkerService(
        private val workerRepository: WorkerRepository,
        private val companyRepository: CompanyRepository
) {

    fun getWorkers(): List<WorkerDto> {
         return workerRepository.findAll().stream()
                .map{ mapWorkerDto(it) }
                 .collect(Collectors.toList())
    }

    fun getWorker(workerId: Long): WorkerDto {
        return workerRepository.findById(workerId)
                .map { mapWorkerDto(it) }
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

    fun mapWorkerDto(worker: Worker): WorkerDto {
        return WorkerDto().apply {
            this.name = worker.name
            this.bithday = worker.birthday
            this.phone = worker.phone
            this.mail = worker.mail
            this.status = worker.status
            this.type = worker.type.toString()
            this.company = worker.company
        }
    }

    fun updateWorker(workerId: Long, updateWorker: WorkerCreateRequest) {
        val originWorker = workerRepository.findById(workerId)
                .orElseThrow { throw WorkerNotFoundException()}

        val company = companyRepository.findByName(updateWorker.company!!)
                .orElseThrow { throw WorkerNotFoundException() }

        originWorker.name = updateWorker.name
        originWorker.birthday = updateWorker.bithday
        originWorker.mail = updateWorker.mail
        originWorker.phone = updateWorker.phone
        originWorker.type = WorkerType.valueOf(updateWorker.type!!)
        originWorker.company = company

        workerRepository.save(originWorker)

    }

}