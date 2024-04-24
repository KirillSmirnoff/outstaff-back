package ru.k2.outstaff.repository.entity

import javax.persistence.*

@Entity
@Table(name = "users_roles")
class UserRole(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "n_id")
        var id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "n_user_id")
        var user: User? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "n_role_id")
        var role: Role? = null
) {

    fun setUsers(user: User?) {
        this.user = user
        this.user?.userRoles?.add(this)
    }

    fun setRoles(role: Role?) {
        this.role = role
        this.role?.userRoles?.add(this)
    }
}