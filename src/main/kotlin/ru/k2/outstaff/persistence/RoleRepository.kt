package ru.k2.outstaff.persistence

import org.springframework.data.jpa.repository.JpaRepository
import ru.k2.outstaff.persistence.entity.Role

interface RoleRepository: JpaRepository<Role, Long> {

    fun findByRoleNameIn(roles: List<String>): List<Role>

    fun findByIdIn(roleIds: List<Long?>): List<Role>
}