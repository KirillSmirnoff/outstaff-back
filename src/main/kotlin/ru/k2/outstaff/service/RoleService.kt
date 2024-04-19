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
import java.time.LocalDateTime
import java.util.stream.Collectors
import javax.persistence.EntityManagerFactory

@Service
class RoleService(private val roleRepository: RoleRepository,
                  @Lazy private val entityManagerFactory: EntityManagerFactory) {

    var roles = mutableMapOf<String, Long>()

    fun getAll(): MutableList<RoleDto> {
        return roleRepository.findAll().stream()
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
        updateRoleMap(role)
        return mapRoleDto(role)
    }

    @Transactional
    fun remove(roleId: Long) {
        roleRepository.deleteById(roleId)
        roles.values.remove(roleId)
    }

    fun update(roleId: Long, updateRole: RoleUpdateRequest): RoleDto {
        val role = roleRepository.findById(roleId)
                .orElseThrow { throw RoleNotFoundException("Role with id [$roleId] not found.") }

        role.apply {
            this.comment = updateRole.comment
            this.date = LocalDateTime.now()
            this.roleName = updateRole.roleName
        }

        val updatedRole = roleRepository.save(role)
        updateRoleMap(updatedRole)

        return mapRoleDto(updatedRole)
    }

    fun getRolesByIds(roleIds: List<Long?>): List<Role> {
        return roleRepository.findAllById(roleIds)
    }

    fun getInit() {
        roleRepository.findAll().stream()
                .forEach { r -> roles[r.roleName!!] = r.id!! }
    }

    private fun updateRoleMap(role: Role){
        roles[role.roleName!!] = role.id!!
    }

    /**
     * Проверяет что роли имеются в системе
     * @see LoadService
     */
    fun checkRoles(roleNames: List<String>): List<Long?> {
        return roleNames.stream()
                .filter { roleName -> roles.containsKey(roleName) }
                .map { roleName -> roles[roleName] }
                .collect(Collectors.toList())
    }

    private fun mapRoleDto(role: Role): RoleDto {
        return RoleDto(role.id!!, role.roleName!!, role.date!!).apply { comment = role.comment }
    }

}