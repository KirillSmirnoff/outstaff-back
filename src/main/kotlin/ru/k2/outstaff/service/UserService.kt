package ru.k2.outstaff.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.k2.outstaff.exceptions.RoleNotFoundException
import ru.k2.outstaff.exceptions.UserNotFoundException
import ru.k2.outstaff.persistence.UserRepository
import ru.k2.outstaff.persistence.UsersRoleRepository
import ru.k2.outstaff.persistence.dto.users.UserCreateRequest
import ru.k2.outstaff.persistence.dto.users.UserRoleDto
import ru.k2.outstaff.persistence.dto.users.UserUpdateRequest
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
    fun getUsersWithRoles(deleted: Boolean): MutableList<UserRoleDto> {
        val mutableList = mutableListOf<UserRoleDto>()
        val isDeleted = if (deleted) 1 else 0
        val users = userRepository.findAll(isDeleted)

        for (user in users) {
            val userRoleDto = mapUserToUserRoleDto(user)
            mutableList.add(userRoleDto)
        }
        return mutableList
    }

    @Transactional
    fun getUser(id: Long): UserRoleDto{
        val user = userRepository.getById(id)
        return mapUserToUserRoleDto(user)
    }

    @Transactional
    fun createUser(newUserRequest: UserCreateRequest) {
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
    }

    @Transactional
    fun removeUser(userId: Long){
        println("Start remove user with id - $userId")
        userRepository.removeById(userId)
    }

    @Transactional
    fun updateUser(userId: Long, updateUser: UserUpdateRequest) {
        val listRoleIDs = roleService.checkRoles(updateUser.roles)
        if (listRoleIDs.isNullOrEmpty())
            throw RoleNotFoundException("role ${updateUser.roles} does not exist")

        userRepository.findById(userId)
                .ifPresentOrElse({ processUpdateUser(it, updateUser, listRoleIDs) }, { throw UserNotFoundException("User with id [$userId] not found.") })
    }

    private fun processUpdateUser(user: User, updateUser: UserUpdateRequest, listRoleIDs: List<Long?>) {
        val rolesForUpdate = roleService.getRolesByIds(listRoleIDs)

        val listUserRolesIds = user.userRoles!!.stream()
                .map { role -> role.id }
                .collect(Collectors.toList())

        usersRoleRepository.deleteByIds(listUserRolesIds)

        for (role in rolesForUpdate) { //todo нужно улучшить запросов чтолько же скольк в цикле записей
            val userRole = UserRole().apply {
                this.user = user
                this.role = role
            }
            usersRoleRepository.save(userRole)
        }

        val updatedUser = user.apply {
            username = updateUser.userName
            phone = updateUser.phone ?: phone
            mail = updateUser.mail ?: mail
            deleted = updateUser.deleted ?: deleted
        }

        userRepository.saveAndFlush(updatedUser)
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