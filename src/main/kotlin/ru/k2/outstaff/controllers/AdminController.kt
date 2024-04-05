package ru.k2.outstaff.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.k2.outstaff.dto.roles.RoleCreateRequest
import ru.k2.outstaff.dto.roles.RoleDto
import ru.k2.outstaff.dto.roles.RoleUpdateRequest
import ru.k2.outstaff.dto.users.UserCreateRequest
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
    fun getRoles(@RequestParam(defaultValue = "false") deleted: Boolean): ResponseEntity<List<RoleDto>>{
        val roles = roleService.getAll(deleted)
        return ResponseEntity.ok(roles)
    }

    @GetMapping("/role/{roleId}")
    fun getRole(@PathVariable("roleId") id: Long): ResponseEntity<RoleDto>{
        val role = roleService.getRole(id)
        return ResponseEntity.ok(role)
    }

    @PostMapping("/role")
    @ResponseStatus(HttpStatus.CREATED)
    fun roleRegister(@RequestBody newRole: RoleCreateRequest){
        roleService.save(newRole)
    }

    @DeleteMapping("/role/{roleId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun removeRole(@PathVariable("roleId") roleId: String){
        roleService.remove(roleId)
    }

    @PutMapping("/role/{roleId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateRole(@PathVariable("roleId") roleId: String, @RequestBody updateRole: RoleUpdateRequest){
        roleService.update(roleId, updateRole)
    }


//   --------------- users -------------------

    @GetMapping("/users")
    fun getUsers(@RequestParam(defaultValue = "false") deleted: Boolean ): ResponseEntity<List<UserRoleDto>> {
        val all = userService.getUsersWithRoles(deleted)
        return ResponseEntity.ok(all)
    }

    @GetMapping("/user/{userId}")
    fun getUser(@PathVariable("userId") id: Long): ResponseEntity<UserRoleDto>{
        val user = userService.getUser(id)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    fun userRegister(@RequestBody roleDto: UserCreateRequest) {
        userService.createUser(roleDto)
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun removeUser(@PathVariable("userId") userId: Long){
        userService.removeUser(userId)
    }

    @PutMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateUser(@PathVariable("userId") roleId: Long, @RequestBody userUpdateRequest: UserUpdateRequest){
        userService.updateUser(roleId, userUpdateRequest)
    }


}