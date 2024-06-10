package ru.k2.outstaff.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.k2.outstaff.dto.roles.RoleCreateRequest
import ru.k2.outstaff.dto.roles.RoleDto
import ru.k2.outstaff.dto.roles.RoleUpdateRequest
import ru.k2.outstaff.dto.users.UserCreateRequest
import ru.k2.outstaff.dto.users.UserDto
import ru.k2.outstaff.dto.users.UserRoleDto
import ru.k2.outstaff.dto.users.UserUpdateRequest
import ru.k2.outstaff.service.RoleService
import ru.k2.outstaff.service.UserService

@RestController
@RequestMapping("/admin")
@Validated
class AdminController(private val roleService: RoleService,
                      private val userService: UserService) {

    @GetMapping("/roles")
    fun listRoles(): ResponseEntity<List<RoleDto>> {
        val roles = roleService.getAll()
        return ResponseEntity.ok(roles)
    }

    @GetMapping("/role/{roleId}")
    fun getRole(@PathVariable("roleId") id: Long): ResponseEntity<RoleDto> {
        val role = roleService.getRole(id)
        return ResponseEntity.ok(role)
    }

    @PostMapping("/role")
    fun createRole(@RequestBody newRole: RoleCreateRequest): ResponseEntity<RoleDto> {
        val role = roleService.save(newRole)
        return ResponseEntity(role, HttpStatus.CREATED)
    }

    @DeleteMapping("/role/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteRole(@PathVariable("roleId") roleId: Long) {
        roleService.remove(roleId)
    }

    @PutMapping("/role/{roleId}")
    fun updateRole(@PathVariable("roleId") roleId: Long,
                   @RequestBody updateRole: RoleUpdateRequest): ResponseEntity<RoleDto> {
        val role = roleService.update(roleId, updateRole)
        return ResponseEntity.ok(role)
    }


//   --------------- users -------------------

    @GetMapping("/users")
    fun listUsers(@RequestParam(defaultValue = "false") deleted: Boolean): ResponseEntity<List<UserDto>> {
        val users = userService.getUsers(deleted)
        return ResponseEntity.ok(users)
    }

    @GetMapping("/user/{userId}")
    fun getUser(@PathVariable("userId") id: Long,
                @RequestParam(defaultValue = "false") deleted: Boolean): ResponseEntity<UserRoleDto> {
        val user = userService.getUser(id, deleted)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/user")
    fun createUser(@RequestBody roleDto: UserCreateRequest): ResponseEntity<UserRoleDto> {
        val user = userService.createUser(roleDto)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteUser(@PathVariable("userId") userId: Long) {
        userService.removeUser(userId)
    }

    @PutMapping("/user/{userId}")
    fun updateUser(@PathVariable("userId") roleId: Long,
                   @RequestBody userUpdateRequest: UserUpdateRequest): ResponseEntity<UserRoleDto> {
        val user = userService.updateUser(roleId, userUpdateRequest)
        return ResponseEntity.ok(user)
    }


}