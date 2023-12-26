package ru.k2.outstaff.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.k2.outstaff.persistence.entity.Role

interface RoleRepository: JpaRepository<Role, Long> {

    @Query("select r from Role r where r.roleName in :roleName")
    fun findByName(@Param(value = "roleName") name: List<String>):List<Role>

//    используем @NamedQuery(name = "Role.findAll")
    fun findAll(@Param("deleted") deleted: Int):List<Role>
}