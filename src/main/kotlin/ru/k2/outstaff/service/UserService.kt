package ru.k2.outstaff.service

import org.springframework.stereotype.Service
import ru.k2.outstaff.persistence.UserRepository
import ru.k2.outstaff.persistence.dto.users.UserRoleDto
import java.util.stream.Collectors
import javax.transaction.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun getAllUsersWithRoles(): MutableList<UserRoleDto> {
        val mutableList = mutableListOf<UserRoleDto>()
        val users = userRepository.findAll()

        for (user in users){
            val userRoleDto = UserRoleDto().apply {
                id = user.id
                userName = user.username
                password = "*****"
                login = user.login
                phone = user.phone
                mail = user.mail
                roles = user.userRole!!.stream()
                        .map { r -> r.role!!.roleName }
                        .collect(Collectors.toList()) as List<String>?
            }
            mutableList.add(userRoleDto)
        }

        return mutableList
    }
}