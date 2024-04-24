package ru.k2.outstaff.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.k2.outstaff.repository.UserRepository
import java.util.stream.Collectors

@Service
class SecUserService(private val userRepository: UserRepository) : UserDetailsService {

    private fun loadByName(username: String?): ru.k2.outstaff.repository.entity.User {
        return if (username != null) userRepository.findByLogin(username) else throw UsernameNotFoundException("Пользователь $username не найден !")
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val userEntity = loadByName(username)

        return User(userEntity.username, userEntity.passsword, mapRolesToAuthorities(userEntity))
    }

    private fun mapRolesToAuthorities(userEntity: ru.k2.outstaff.repository.entity.User): Collection<GrantedAuthority?>? {
        val roles = userEntity.userRoles

        return if (roles != null) roles.stream()
                .map { r -> SimpleGrantedAuthority(r.role?.roleName) }
                .collect(Collectors.toList())

                else  throw UsernameNotFoundException("Не найдены права пользователя ${userEntity.username}")
    }

}