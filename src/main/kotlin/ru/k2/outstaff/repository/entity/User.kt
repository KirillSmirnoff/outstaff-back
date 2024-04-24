package ru.k2.outstaff.repository.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "n_id")
        var id:Long? = null,

        @Column(name = "c_username")
        var username: String? = null,

        @Column(name = "c_login")
        var login: String? = null,

        @Column(name = "c_password")
        var passsword: String? = null,

        @Column(name = "c_phone")
        var phone: String? = null,

        @Column(name = "c_mail")
        var mail: String? = null,

        @Column(name = "n_delete")
        var deleted: Int = 0,

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        var userRoles: MutableList<UserRole>? = mutableListOf()

)