package ru.k2.outstaff.service

import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.k2.outstaff.exceptions.RoleNotFoundException
import ru.k2.outstaff.persistence.RoleRepository
import ru.k2.outstaff.dto.roles.RoleCreateRequest
import ru.k2.outstaff.dto.roles.RoleDto
import ru.k2.outstaff.dto.roles.RoleUpdateRequest
import ru.k2.outstaff.persistence.entity.Role
import ru.k2.outstaff.utils.Util
import java.time.LocalDateTime
import java.util.stream.Collectors

@Service
class RoleService(private val roleRepository: RoleRepository,
                  @Lazy private val loadService: LoadService) {

    fun getAll(isDeleted: Boolean): MutableList<RoleDto> {
        val deleted = if (isDeleted) 1 else 0

        return roleRepository.findAll().stream()
                .filter { r -> r.deleted <= deleted }
                .map { r -> mapRoleDto(r) }
                .collect(Collectors.toList())
    }

    @Transactional(readOnly = true)
    fun getRole(id: Long): RoleDto {
        return roleRepository.getById(id)
                .let { mapRoleDto(it) }
    }

    @Transactional
    fun save(newRole: RoleCreateRequest): RoleDto {
        val newRoleEntity = Role().apply {
            roleName = newRole.roleName.toUpperCase()
            comment = if (newRole.comment.isNullOrEmpty()) "" else newRole.comment
        }
        val role = roleRepository.save(newRoleEntity)

//        loadService.loadRoles()

        return RoleDto(role.id!!, role.roleName!!, role.date!!, role.deleted, role.comment)
    }

    @Transactional
    fun remove(roleId: Long){
        roleRepository.deleteById(roleId)
//        loadService.loadRoles()
    }

    fun update(roleId: Long, updateRole: RoleUpdateRequest): RoleDto {
        val role = roleRepository.findById(roleId)
                .orElseThrow { throw RoleNotFoundException("Role with id [$roleId] not found.") }

        role.apply {
            this.comment = updateRole.comment
            this.date = LocalDateTime.now()
            this.deleted = updateRole.deleted!!
            this.roleName = updateRole.roleName
        }

        val updatedRole = roleRepository.save(role)
//        loadService.loadRoles()

        return mapRoleDto(updatedRole)
    }

    fun getRolesByIds(roleIds: List<Long?>): List<Role> {
        return roleRepository.findByIdIn(roleIds)
    }

    /**
     * Проверяет что роли имеются в системе
     * @see LoadService
     */
    fun checkRoles(roles: List<String>): List<Long?> {
        return roles.stream()
                .filter { role -> Util.roles.containsKey(role) }
                .map { role -> Util.roles[role] }
                .collect(Collectors.toList())
    }

    private fun mapRoleDto(role: Role): RoleDto {
        return RoleDto(role.id!!, role.roleName!!, role.date!!, role.deleted
        ).apply { comment = role.comment }
    }

}