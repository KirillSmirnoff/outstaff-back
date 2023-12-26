package ru.k2.outstaff.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.k2.outstaff.persistence.entity.UserRole

interface UsersRoleRepository: JpaRepository<UserRole, Long> {

    @Query("delete from UserRole u where u.user = :userId and u.role not in (:ids)")
    fun deleteById(@Param("ids") roleIds: List<Long?>, userId: Long): Void

//    @Query("select u from UserRole u join fetch u.role where u.user.deleted <= :deleted")
//    fun findAll(@Param("deleted") deleted:Int): List<UserRole>
}