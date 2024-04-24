package ru.k2.outstaff.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.k2.outstaff.repository.entity.UserRole

interface UsersRoleRepository: JpaRepository<UserRole, Long> {

    fun deleteByIdIn(listUserRolesIds: List<Long?>)
}