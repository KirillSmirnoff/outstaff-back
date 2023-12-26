package ru.k2.outstaff.service

import org.springframework.stereotype.Service
import ru.k2.outstaff.persistence.UserRepository
import ru.k2.outstaff.persistence.UsersRoleRepository
import ru.k2.outstaff.persistence.dto.users.UserRoleDto
import java.util.stream.Collectors
import javax.transaction.Transactional

@Service
class UserService(private val userRepository: UserRepository,
                  private val userRoleRepository: UsersRoleRepository) {

    @Transactional
    fun getUsersWithRoles(deleted: Boolean): MutableList<UserRoleDto> {
        val mutableList = mutableListOf<UserRoleDto>()
        val isDeleted = if (deleted) 1 else 0
        val users = userRepository.findAll()
//        val users = userRepository.findAll(isDeleted)
//        val users = userRoleRepository.findAll(isDeleted)
//        val users = userRoleRepository.findAll()
//        val users = userRepository.findAll()
//        val user = users[0]

        for (user in users){
            val userRoleDto = UserRoleDto().apply {
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
            mutableList.add(userRoleDto)
        }

        return mutableList
    }
}