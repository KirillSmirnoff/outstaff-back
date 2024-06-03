package ru.k2.outstaff.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import ru.k2.outstaff.AbstractDataBaseContainer
import ru.k2.outstaff.dto.roles.RoleCreateRequest
import ru.k2.outstaff.dto.roles.RoleUpdateRequest
import ru.k2.outstaff.repository.RoleRepository
import javax.persistence.EntityNotFoundException

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
internal class RoleServiceTest: AbstractDataBaseContainer() {

    companion object {
        lateinit var service: RoleService

        @JvmStatic
        @BeforeAll
        fun init(@Autowired repository: RoleRepository) {
            service = RoleService(repository)
        }

    }

    @Test
    fun getAll() {
        //Given
        //When
        assertEquals(2, service.getAll().size)
        //Then
    }

    @Test
    fun getRole() {
        //Given
        val roleId: Long = 115
        //When
        val role = service.getRole(roleId)
        //Then
        assertEquals(roleId, role.roleId)
    }

    @Test
    fun save() {
        //Given
        val roleCreateRequest = RoleCreateRequest("TESTROLE", "The role for testing")
        val sizeBeforeInsert = service.getAll().size
        //When
        val role = service.save(roleCreateRequest)
        val sizeAfterInsert = service.getAll().size
        //Then
        assertEquals(sizeBeforeInsert+1, sizeAfterInsert)
        assertEquals(roleCreateRequest.roleName, role.roleName)
    }

    @Test
    fun remove() {
        //Given
        val roleId: Long = 115
        val sizeBeforeExecuting = service.getAll().size
        //When
        service.remove(roleId)
        val sizeAfterExecuting = service.getAll().size
        //Then
        assertEquals(sizeBeforeExecuting-1, sizeAfterExecuting)
        assertThrows(EntityNotFoundException::class.java, { service.getRole(roleId) })
    }

    @Test
    fun update() {
        //Given
        val roleId: Long = 115
        val givenRole = service.getRole(roleId)
        //When
        val updateRole = service.update(roleId, RoleUpdateRequest("TestRole", "Role for testing"))
        //Then
        assertNotEquals(givenRole.roleName, updateRole.roleName)
        assertEquals(roleId, updateRole.roleId)
        assertEquals("TestRole", updateRole.roleName)
    }

    @Test
    fun `get roles by existed Id's`() {
        //Given
        val roleIds = listOf<Long>(111, 115)
        //When
        val roles = service.getRolesByIds(roleIds)
        //Then
        assertTrue(roles.isNotEmpty())
    }

    @Test
    fun `get roles by non-existed Id's`() {
        //Given
        val roleIds = listOf<Long>(117, 125)
        //When
        val roles = service.getRolesByIds(roleIds)
        //Then
        assertFalse(roles.isNotEmpty())
    }

    @Test
    fun getInit() {
        //Given
        val roles = service.roles
        roles.clear()
        val initRoleSize = roles.size
        //When
        service.getInit()
        val sizeAfterInit = service.getAll().size
        //Then
        assertNotEquals(initRoleSize, sizeAfterInit)
    }

    @Test
    fun `check roles with existed names`() {
        //Given
        service.getInit()
        val rolesNames = listOf<String>("ADMIN", "MANAGER")
        //When
        val checkRoles = service.checkRoles(rolesNames)
        //Then
        assertTrue(checkRoles.isNotEmpty())
    }

    @Test
    fun `check roles with non-existed names`() {
        //Given
        service.getInit()
        val rolesNames = listOf<String>("CHOCKO-PIE", "TEST")
        //When
        val checkRoles = service.checkRoles(rolesNames)
        //Then
        assertFalse(checkRoles.isNotEmpty())
    }
}