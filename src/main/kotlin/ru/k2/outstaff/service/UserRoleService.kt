package ru.k2.outstaff.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.k2.outstaff.exceptions.RoleNotFoundException
import ru.k2.outstaff.persistence.RoleRepository
import ru.k2.outstaff.persistence.UserRepository
import ru.k2.outstaff.persistence.UsersRoleRepository
import ru.k2.outstaff.persistence.dto.users.UserCreateRequest
import ru.k2.outstaff.persistence.entity.UserEntity
import ru.k2.outstaff.persistence.entity.UserRoleEntity
import ru.k2.outstaff.utils.Util

@Service
class UserRoleService(private val usersRoleRepository: UsersRoleRepository,
                      private val passwordEncoder: PasswordEncoder,
                      private val roleRepository: RoleRepository,
                      private val userRepository: UserRepository) {

    fun saveUser(newUserRequest: UserCreateRequest) {
        val roleNameInDataBase = if (!newUserRequest.roles.isNullOrEmpty()) {
            val newUserRoles = newUserRequest.roles
            for (role in newUserRoles) {
                if (!Util.roles!!.contains(role))
                    throw RoleNotFoundException()
            }
            roleRepository.findByName(newUserRequest.roles)
        } else {
            roleRepository.findByName(mutableListOf("MANAGER"))
        }

        val userEntity = UserEntity().apply {
            username = newUserRequest.userName
            login = newUserRequest.login
            passsword = passwordEncoder.encode(newUserRequest.password)
            phone = newUserRequest.phone
            mail = newUserRequest.mail
        }

        val saveUser = userRepository.saveAndFlush(userEntity)

        if (!roleNameInDataBase.isNullOrEmpty()) {
            for (curRole in roleNameInDataBase) {
                val userRole = UserRoleEntity().apply {
                    user = saveUser
                    role = curRole
                }
                usersRoleRepository.saveAndFlush(userRole)
            }
        }
    }
}