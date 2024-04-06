package ru.k2.outstaff.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.k2.outstaff.dto.worker.WorkerCreateRequest
import ru.k2.outstaff.dto.worker.WorkerDto
import ru.k2.outstaff.service.WorkerService

@RestController
@RequestMapping("/worker")
class WorkerController(private val workerService: WorkerService) {

    @GetMapping()
    fun listWorkers(): ResponseEntity<List<WorkerDto>> {
        val workers = workerService.getWorkers()
        return ResponseEntity.ok(workers)
    }

    @GetMapping("/{workerId}")
    fun getWorker(@PathVariable("workerId") workerId: Long): ResponseEntity<WorkerDto> {
        val worker = workerService.getWorker(workerId)
        return ResponseEntity.ok(worker)
    }

    @PutMapping("/{workerId}")
    fun updateWorker(@PathVariable("workerId") workerId: Long,
                     @RequestBody worker: WorkerCreateRequest): ResponseEntity<WorkerDto>{
        val updatedWorker = workerService.updateWorker(workerId, worker)
        return ResponseEntity.ok(updatedWorker)
    }

    @DeleteMapping("/{workerId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteWorker(@PathVariable("workerId") workerId: Long) {
        return workerService.deleteWorker(workerId)
    }

    @PostMapping
    fun createWorker(@RequestBody request: WorkerCreateRequest): ResponseEntity<WorkerDto>{
        val worker = workerService.createWorker(request)
        return ResponseEntity(worker, HttpStatus.CREATED)
    }
}