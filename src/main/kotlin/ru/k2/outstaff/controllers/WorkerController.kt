package ru.k2.outstaff.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.k2.outstaff.persistence.dto.worker.WorkerCreateRequest
import ru.k2.outstaff.persistence.dto.worker.WorkerDto
import ru.k2.outstaff.service.WorkerService

@RestController
@RequestMapping("/worker")
class WorkerController(private val workerService: WorkerService) {

    @GetMapping("/manage/{userId}")
    fun getWorkers(@PathVariable("userId") userId: Long) {
        workerService.getWorkersByUserId(userId)
    }

    @GetMapping("/{workerId}")
    fun getWorker(@PathVariable("workerId") workerId: Long): WorkerDto {
        return workerService.getWorker(workerId)
    }

    @DeleteMapping("/{workerId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteWorker(@PathVariable("workerId") workerId: Long) {
        return workerService.deleteWorker(workerId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createWorker(@RequestBody request: WorkerCreateRequest){
        workerService.createWorker(request)
    }
}