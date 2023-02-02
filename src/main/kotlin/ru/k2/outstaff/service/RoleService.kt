package ru.k2.outstaff.service

import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import ru.k2.outstaff.exceptions.RoleNotFoundException
import ru.k2.outstaff.persistence.RoleRepository
import ru.k2.outstaff.persistence.dto.roles.RoleCreateRequest
import ru.k2.outstaff.persistence.dto.roles.RoleDto
import ru.k2.outstaff.persistence.dto.roles.RoleUpdateRequest
import ru.k2.outstaff.persistence.entity.RoleEntity
import java.time.LocalDateTime

@Service
class RoleService(private val roleRepository: RoleRepository,
                  @Lazy private val loadService: LoadService) {

    fun findRoles(isDeleted: Boolean): MutableList<RoleDto> {
        val listOfRoles = mutableListOf<RoleDto>()
        val deleted = if (isDeleted) 1 else 0

        val findAll = roleRepository.findAll(deleted)
        for (role in findAll) {
//            if (role.deleted <= deleted) {
                listOfRoles.add(
                        RoleDto(role.id!!, role.roleName!!, role.date!!, role.deleted!!
                        ).apply { comment = role.comment }
                )
//            }
        }
        return listOfRoles
    }

    fun saveRole(newRole: RoleCreateRequest) {
        val newRoleEntity = RoleEntity().apply {
            roleName = newRole.roleName.toUpperCase()
            comment = if (newRole.comment.isNullOrEmpty()) "" else newRole.comment
        }
        roleRepository.saveAndFlush(newRoleEntity)

        loadService.loadRoles()
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

    fun update(roleId: String, updateRole: RoleUpdateRequest) {
        val role = roleRepository.findById(roleId.toLong())
        if (role.isPresent) {
            role.get().apply {
                this.comment = updateRole.comment
                this.date = LocalDateTime.now()
                this.deleted = updateRole.deleted!!
                this.roleName = updateRole.roleName
            }.also { roleRepository.saveAndFlush(it) }
            loadService.loadRoles()
        } else throw RoleNotFoundException("Role with id [$roleId] not found.")
    }
}