package ru.k2.outstaff.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.k2.outstaff.persistence.entity.User
import java.util.Optional

interface UserRepository: JpaRepository<User, Long>{

    fun findByLogin(name: String):User

    @Query("update User u set u.deleted = 1 where u.id = :id")
    @Modifying
    fun removeById(@Param("id") userId: Long)

    @Query("select u from User u left join fetch u.userRoles where u.id = :id")
    fun obtainById(id: Long): Optional<User>
}