package ru.k2.outstaff

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.k2.outstaff.persistence.*
import ru.k2.outstaff.persistence.entity.Role
import ru.k2.outstaff.persistence.entity.User
import ru.k2.outstaff.persistence.entity.UserRole
import javax.transaction.Transactional

@SpringBootTest
class RepositoryTest
@Autowired constructor(val companyRepository: CompanyRepository,
                       val workerRepository: WorkerRepository,
                       val userRepository: UserRepository,
                       val roleRepository: RoleRepository,
                       val usersRoleRepository: UsersRoleRepository
) {

    @Test
    fun `test companyRepository`() {
        companyRepository.findAll().forEach { cmp -> println(cmp) }
    }

    @Test
    fun `test workerRepository`() {
        workerRepository.findAll().forEach { wrk -> println(wrk.companyId?.companyName) }
    }

    @Test
    fun `test company query`(){
        val company = companyRepository.findByName("SAID COMPANY")
        println(company.companyName +" - "+ company.id)
    }

    @Test
    fun `test user roles`(){
        val userEntity = User().apply {
            username = "Вован Петрович Жук"
            login = "wow"
            passsword = "0"
        }

        val roleEntity = Role().apply {
            roleName = "TEST"
        }

        userRepository.saveAndFlush(userEntity)
        roleRepository.saveAndFlush(roleEntity)



        val usersRoleEntity = UserRole().apply {
            user = userEntity
            role = roleEntity
        }
        usersRoleRepository.saveAndFlush(usersRoleEntity)
    }

    @Test
    @Transactional
    fun `find user by name`(){
        val user = userRepository.findByName("wow")

        val usersRole = user.userRoles
        println(usersRole?.get(0)?.role?.roleName)

        Assertions.assertTrue("Вован Петрович Жук".equals(user.username))
    }

    @Test
    fun `find role by name`(){
        val role = roleRepository.findByName(listOf("BOSS", "MANAGER"))
        println("Return roleName ${role[0].roleName} and  ${role[1].roleName}")
    }

    @Test
    @Transactional
    fun `get user roles`() {
        val users = userRepository.findAll()
        for (user in users) {
//            for (userRole in user.userRole!!) {
                println("User: ${user.username} has role ${user.userRoles}")
//            }
        }
    }
}