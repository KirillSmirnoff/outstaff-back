package ru.k2.outstaff.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.k2.outstaff.persistence.entity.RoleEntity

interface RoleRepository: JpaRepository<RoleEntity, Long> {

    @Query("select r from RoleEntity r where r.roleName in :roleName")
    fun findByName(@Param(value = "roleName") name: List<String>):List<RoleEntity>

    //используем @NamedQuery(name = "RoleEntity.findAll")
    fun findAll(@Param("deleted") deleted: Int):List<RoleEntity>
}