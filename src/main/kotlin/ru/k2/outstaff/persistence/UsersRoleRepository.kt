package ru.k2.outstaff.persistence

import org.springframework.data.jpa.repository.JpaRepository
import ru.k2.outstaff.persistence.entity.UserRole

interface UsersRoleRepository: JpaRepository<UserRole, Long> {

    fun deleteByIdIn(listUserRolesIds: List<Long?>)
}