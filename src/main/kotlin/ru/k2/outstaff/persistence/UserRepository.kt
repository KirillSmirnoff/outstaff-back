package ru.k2.outstaff.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.k2.outstaff.persistence.entity.User
import java.util.*

interface UserRepository: JpaRepository<User, Long> {

    @Query("select u from User u LEFT JOIN fetch u.userRoles where u.login = :name")
    fun findByName(@Param("name") name: String):User

    @Query("update User u set u.deleted = 1 where u.id = :id")
    @Modifying
    fun removeById(@Param("id") userId: Long)

//    @Query("select u from UserEntity u left join fetch u.userRole where u.id = :userId")
    fun findUserWithRolesById(@Param("userId") userId: Long): Optional<User>

    /**
     * @see ru.k2.outstaff.persistence.entity.User NamedQuery(name = "User.findAll")
     * */
    fun findAll(@Param("deleted") deleted:Int): List<User>

}