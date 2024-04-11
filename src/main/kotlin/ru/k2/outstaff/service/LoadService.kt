package ru.k2.outstaff.service

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class LoadService(private var roleService: RoleService) {

    @EventListener
    fun serviceInit(event: ApplicationReadyEvent) {
        loadRoles()
    }

    fun loadRoles(){
        roleService.getInit()
    }
}