package ru.k2.outstaff.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.k2.outstaff.dto.company.CompanyDto
import ru.k2.outstaff.dto.worker.WorkerCreateRequest
import ru.k2.outstaff.dto.worker.WorkerDto
import ru.k2.outstaff.service.WorkerService

@RestController
@RequestMapping("/worker")
class WorkerController(private val workerService: WorkerService) {

    @GetMapping()
    fun getWorkers(): List<WorkerDto> {
        return workerService.getWorkers()
    }

    @GetMapping("/{workerId}")
    fun getWorker(@PathVariable("workerId") workerId: Long): WorkerDto {
        return workerService.getWorker(workerId)
    }

    @PutMapping("/{workerId}")
    fun updateWorker(@PathVariable("workerId") workerId: Long, @RequestBody worker: WorkerCreateRequest){
        workerService.updateWorker(workerId, worker)
    }

    @DeleteMapping("/{workerId}")
    fun deleteWorker(@PathVariable("workerId") workerId: Long) {
        return workerService.deleteWorker(workerId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createWorker(@RequestBody request: WorkerCreateRequest){
        workerService.createWorker(request)
    }
}