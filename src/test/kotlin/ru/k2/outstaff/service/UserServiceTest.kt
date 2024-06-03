package ru.k2.outstaff.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles
import ru.k2.outstaff.AbstractDataBaseContainer
import ru.k2.outstaff.dto.users.UserCreateRequest
import ru.k2.outstaff.dto.users.UserUpdateRequest
import ru.k2.outstaff.exceptions.UserNotFoundException
import ru.k2.outstaff.repository.RoleRepository
import ru.k2.outstaff.repository.UserRepository
import ru.k2.outstaff.repository.UsersRoleRepository

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
internal class UserServiceTest : AbstractDataBaseContainer() {

    companion object {
        lateinit var service: UserService

        @JvmStatic
        @BeforeAll
        fun init(@Autowired userRepository: UserRepository,
                 @Autowired roleRepository: RoleRepository,
                 @Autowired usersRoleRepository: UsersRoleRepository) {
            val roleService = RoleService(roleRepository)
            roleService.getInit()
            service = UserService(userRepository, roleService, BCryptPasswordEncoder(), usersRoleRepository)
        }
    }

    @Test
    fun getUsers() {
        //Given
        //When
        val users = service.getUsers(false)
        //Then
        assertTrue(users.isNotEmpty())
    }

    @Test
    fun getUser() {
        //Given
        val userId: Long = 145
        //When
        //Then
        assertDoesNotThrow {  service.getUser(userId, false) }
    }

    @Test
    fun createUser() {
        //Given
        val userCreateRequest = UserCreateRequest("Petr", "petr", "petr",
                "+35465", "petr@mail.ru", listOf("ADMIN"))
        val sizeBeforeInsert = service.getUsers(false).size
        //When
        val user = service.createUser(userCreateRequest)
        val sizeAfterInsert = service.getUsers(false).size
        //Then
        assertEquals(sizeBeforeInsert+1, sizeAfterInsert)
        assertEquals(userCreateRequest.userName, user.userName)
    }

    @Test
    fun updateUser() {
        //Given
        val userId: Long = 165
        val userUpdateRequest = UserUpdateRequest("Petr", "petr", "petr",
                0, listOf("MANAGER")
        )
        //When
        val user = service.updateUser(userId, userUpdateRequest)
        //Then
        assertEquals(userUpdateRequest.userName, user.userName)
        assertEquals(userUpdateRequest.roles[0], user.roles?.get(0))
    }

    @Test
    fun removeUser() {
        //Given
        val userId: Long = 165
        //When
        service.removeUser(userId)
        //Then
        assertThrows(UserNotFoundException::class.java) { service.removeUser(userId) }

    }
}