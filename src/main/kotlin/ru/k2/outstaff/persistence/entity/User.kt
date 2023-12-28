package ru.k2.outstaff.persistence.entity

import org.hibernate.annotations.BatchSize
import javax.persistence.*

@NamedQuery(name = "User.findAll",
//        query = "select u from UserEntity u join fetch u.userRole where u.deleted <= :deleted")
        query = "select u from User u where u.deleted <= :deleted")

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
        @BatchSize(size = 5)
        var userRoles: MutableList<UserRole>? = null

)