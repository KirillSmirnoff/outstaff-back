package ru.k2.outstaff.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.k2.outstaff.repository.entity.Role

interface RoleRepository: JpaRepository<Role, Long> {

    fun findByRoleNameIn(roles: List<String>): List<Role>

    fun findByIdIn(roleIds: List<Long?>): List<Role>
}