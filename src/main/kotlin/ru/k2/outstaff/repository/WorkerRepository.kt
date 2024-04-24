package ru.k2.outstaff.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.k2.outstaff.repository.entity.Worker

@Repository
interface WorkerRepository : JpaRepository<Worker, Long>{

    @Query("select w from Worker w join fetch w.company")
    fun getAll(): List<Worker>
}