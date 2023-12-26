package ru.k2.outstaff.service

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.k2.outstaff.persistence.dto.roles.RoleDto
import ru.k2.outstaff.utils.Util
import java.util.stream.Collectors

@Component
class LoadService(private var roleService: RoleService) {

    @EventListener
    fun serviceInit(event: ApplicationReadyEvent) {
        loadRoles()
    }

    fun loadRoles(){
        Util.roles = roleService.getAll(false).stream()
                .collect(Collectors.toUnmodifiableMap(RoleDto::roleName, RoleDto::roleName))
    }
}