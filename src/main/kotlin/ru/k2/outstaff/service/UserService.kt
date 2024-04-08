package ru.k2.outstaff.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.k2.outstaff.exceptions.RoleNotFoundException
import ru.k2.outstaff.exceptions.UserNotFoundException
import ru.k2.outstaff.persistence.UserRepository
import ru.k2.outstaff.persistence.UsersRoleRepository
import ru.k2.outstaff.dto.users.UserCreateRequest
import ru.k2.outstaff.dto.users.UserRoleDto
import ru.k2.outstaff.dto.users.UserUpdateRequest
import ru.k2.outstaff.persistence.entity.User
import ru.k2.outstaff.persistence.entity.UserRole
import java.util.stream.Collectors
import javax.transaction.Transactional

@Service
class UserService(private val userRepository: UserRepository,
                  private val roleService: RoleService,
                  private val passwordEncoder: PasswordEncoder,
                  private val usersRoleRepository: UsersRoleRepository) {

    @Transactional
    fun getUsers(deleted: Boolean): MutableList<UserRoleDto> {
        val isDeleted = if (deleted) 1 else 0

        return userRepository.findAll().stream()
                .filter { u -> u.deleted <= isDeleted }
                .map { u -> mapUserToUserRoleDto(u) }
                .collect(Collectors.toList())
    }

    @Transactional
    fun getUser(id: Long): UserRoleDto{
        return mapUserToUserRoleDto(userRepository.getById(id))
    }

    @Transactional //todo переделать
    fun createUser(newUserRequest: UserCreateRequest): Long {
        // Проеряем что переданная роль валидна.
        val listRoleIDs = roleService.checkRoles(newUserRequest.roles)
        if (listRoleIDs.isNullOrEmpty())
            throw RoleNotFoundException("role ${newUserRequest.roles} does not exist")

        val newUser = User().apply {
            username = newUserRequest.userName
            login = newUserRequest.login
            passsword = passwordEncoder.encode(newUserRequest.password)
            phone = newUserRequest.phone
            mail = newUserRequest.mail
        }
        val saveUser = userRepository.save(newUser)

        val roles = roleService.getRolesByIds(listRoleIDs)
        for (curRole in roles) {
                val userRole = UserRole().apply {
                    this.setUsers(saveUser)
                    this.setRoles(curRole)
                }
            usersRoleRepository.save(userRole)
        }
        return saveUser.id!!
    }

    @Transactional
    fun updateUser(userId: Long, updateUser: UserUpdateRequest): UserRoleDto {
        val listRoleIDs = roleService.checkRoles(updateUser.roles)
        if (listRoleIDs.isNullOrEmpty())
            throw RoleNotFoundException("role ${updateUser.roles} does not exist")

        val user = userRepository.findById(userId)
                .orElseThrow { UserNotFoundException("User with id [$userId] not found.") }

        return mapUserToUserRoleDto(processUpdateUser(user, updateUser, listRoleIDs))
    }

    @Transactional
    fun removeUser(userId: Long){
        println("Start remove user with id - $userId")
        userRepository.removeById(userId)
    }

    private fun processUpdateUser(user: User, updateUser: UserUpdateRequest, listRoleIDs: List<Long?>): User {
        val listUserRolesIds = user.userRoles!!.stream()
                .map { role -> role.id }
                .collect(Collectors.toList())

        user.userRoles!!.clear()
        usersRoleRepository.deleteByIdIn(listUserRolesIds)

        val userRoles = roleService.getRolesByIds(listRoleIDs).stream()
                .map {
                    UserRole().apply {
                        this.setUsers(user)
                        this.setRoles(it)
                    }
                }.collect(Collectors.toList())

        usersRoleRepository.saveAll(userRoles)

        val updatedUser = user.apply {
            username = updateUser.userName
            phone = updateUser.phone ?: phone
            mail = updateUser.mail ?: mail
            deleted = updateUser.deleted ?: deleted
        }

        return userRepository.saveAndFlush(updatedUser)
    }

    private fun mapUserToUserRoleDto(user: User): UserRoleDto {
        return UserRoleDto().apply {
            id = user.id
            userName = user.username
            password = "*****"
            login = user.login
            phone = user.phone
            mail = user.mail
            status = if (user.deleted > 0) "REMOVED" else "ACTIVE"
            roles = user.userRoles!!.stream()
                    .map { r -> r.role!!.roleName }
                    .collect(Collectors.toList()) as List<String>?
        }
    }
}