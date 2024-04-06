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
        val listOfRoles = mutableListOf<RoleDto>()
        val deleted = if (isDeleted) 1 else 0

        val findAll = roleRepository.findAll(deleted)
        for (role in findAll) {
//            if (role.deleted <= deleted) {
                listOfRoles.add(
                        RoleDto(role.id!!, role.roleName!!, role.date!!, role.deleted
                        ).apply { comment = role.comment }
                )
//            }
        }
        return listOfRoles
    }

    fun save(newRole: RoleCreateRequest): RoleDto {
        val newRoleEntity = Role().apply {
            roleName = newRole.roleName.toUpperCase()
            comment = if (newRole.comment.isNullOrEmpty()) "" else newRole.comment
        }
        val role = roleRepository.saveAndFlush(newRoleEntity)

        loadService.loadRoles()

        return RoleDto(role.id!!, role.roleName!!, role.date!!, role.deleted, role.comment)
    }

    fun remove(roleId: String){
        val role = roleRepository.findById(roleId.toLong())
        if(role.isPresent){
            val roleEntity = role.get()
            roleEntity.deleted = 1
            roleRepository.saveAndFlush(roleEntity)

            loadService.loadRoles()
        }
    }

    fun update(roleId: String, updateRole: RoleUpdateRequest): RoleDto {
        val role = roleRepository.findById(roleId.toLong())
                .orElseThrow { throw RoleNotFoundException("Role with id [$roleId] not found.") }

        role.apply {
            this.comment = updateRole.comment
            this.date = LocalDateTime.now()
            this.deleted = updateRole.deleted!!
            this.roleName = updateRole.roleName
        }

        val updatedRole = roleRepository.save(role)
        loadService.loadRoles()

        return RoleDto(updatedRole.id!!, updatedRole.roleName!!, updatedRole.date!!, updatedRole.deleted)
                .apply { comment = updatedRole.comment }
    }


    /***
     * Проеряет что переданная роль валидна.
     * В случае пустой роли, пользователь создается с ролью MANAGER.
     */
    fun getRoles(roles: List<String>?): List<Role> {
        return if (!roles.isNullOrEmpty()) {
            checkRoles(roles) //необходимо замнить на аннотацию
            roleRepository.findByName(roles)
        } else roleRepository.findByName(mutableListOf("MANAGER")) // нужно создавать не MANAGER а пользователя без прав
    }

    fun getRolesByIds(roleIds: List<Long?>): List<Role> {
            return roleRepository.findByIds(roleIds)
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

    @Transactional
    fun getRole(id: Long): RoleDto {
        val role = roleRepository.getById(id)
        return RoleDto(role.id!!, role.roleName!!, role.date!!, role.deleted
        ).apply { comment = role.comment }
    }
}