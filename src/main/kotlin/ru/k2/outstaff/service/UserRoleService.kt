package ru.k2.outstaff.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.k2.outstaff.exceptions.UserNotFoundException
import ru.k2.outstaff.persistence.UserRepository
import ru.k2.outstaff.persistence.UsersRoleRepository
import ru.k2.outstaff.persistence.dto.users.UserCreateRequest
import ru.k2.outstaff.persistence.dto.users.UserRoleDto
import ru.k2.outstaff.persistence.dto.users.UserUpdateRequest
import ru.k2.outstaff.persistence.entity.Role
import ru.k2.outstaff.persistence.entity.User
import ru.k2.outstaff.persistence.entity.UserRole
import java.util.stream.Collectors

@Service
class UserRoleService(private val usersRoleRepository: UsersRoleRepository,
                      private val passwordEncoder: PasswordEncoder,
                      private val roleService: RoleService,
                      private val userRepository: UserRepository) {

    fun createUser(newUserRequest: UserCreateRequest) {
        // Проеряем что переданная роль валидна.
        val roleNameInDataBase = roleService.getRoles(newUserRequest.roles)

        val userEntity = User().apply {
            username = newUserRequest.userName
            login = newUserRequest.login
            passsword = passwordEncoder.encode(newUserRequest.password)
            phone = newUserRequest.phone
            mail = newUserRequest.mail
        }

        val saveUser = userRepository.saveAndFlush(userEntity)

        if (!roleNameInDataBase.isNullOrEmpty()) {
            for (curRole in roleNameInDataBase) {
                val userRole = UserRole().apply {
                    user = saveUser
                    role = curRole
                }
                usersRoleRepository.saveAndFlush(userRole)
            }
        }
    }

    fun removeUser(userId: Long){
        println("Start remove user with id - $userId")
        val user = userRepository.findById(userId)
        if (user.isPresent){
            val entity = user.get()
            entity.deleted = 1
            userRepository.saveAndFlush(entity)
        }
    }

    @Transactional
    fun updateUser(userId: Long, updateUser: UserUpdateRequest) {
        val user = userRepository.findUserWithRolesById(userId)
        if (!user.isPresent) throw UserNotFoundException("User with id [$userId] not found.")

        val rolesForUpdate = roleService.getRoles(updateUser.roles)
        val userRoles = user.get().userRoles!!

        /*
        * Если изменились сами роили а не их колличество
        * Просто обновляем/переназначаем роли
        * */
        if (rolesForUpdate.size == userRoles.size) {
//            IntStream.range(0, rolesForUpdate.size)
//                    .forEach { i -> userRoles[i].role = rolesForUpdate[i] }
        }

        /*
        * Если колличество ролей не совпадает,
        * то простым обновлением не обойтись и нужно создавать/удалять записи в таблице users_roles
        * */
        if (rolesForUpdate.size != userRoles.size) {
//            val rolesForDelete = getRolesForDelete(userRoles, rolesForUpdate)
//            val rolesForAdd = getRolesForAdd(userRoles, rolesForUpdate)

//            val roleIds = rolesForUpdate.stream()
//                    .map(RoleEntity::id)
//                    .collect(Collectors.toList())

//            usersRoleRepository.deleteById(roleIds, user.get().id!!)


//            val newUserRoles = mutableListOf<UserRoleEntity>()
//            rolesForUpdate.forEach{ r -> newUserRoles.add(UserRoleEntity().apply {
//                this.setUsers(user.get())
//                this.setRoles(r)
//            })}

//            usersRoleRepository.saveAndFlush()


//            user.get().userRole = newUserRoles

        }

//        val updatedUser = user.get().apply {
//            username = updateUser.userName
//            phone = updateUser.phone ?: phone
//            mail = updateUser.mail ?: mail
//            deleted = updateUser.deleted ?: deleted
//        }
//
//        userRepository.saveAndFlush(updatedUser)

    }

    private fun getRolesForDelete(userRoles : List<UserRole>, updateRoles : List<Role>): List<UserRole> {
        val roles = mutableListOf<UserRole>()
        for (userRole in userRoles){
            if (!updateRoles.contains(userRole.role))
                roles.add(userRole)
        }
        return roles
    }

//    private fun getRolesForAdd(userRoles : List<UserRole>, updateRoles : List<Role>): List<UserRole> {
//        val roles = mutableListOf<UserRole>()
//        val userRoles = userRoles.stream()
//                .map { u -> u.role }
//                .collect(Collectors.toList())
//
//        for (role in updateRoles){
//            if (!userRoles.contains(role))
//                roles.add(UserRole().apply {
//                    setUsers(userRoles[0].user)
//                    setRoles(role)
//                })
//        }
//        return roles
//    }
}