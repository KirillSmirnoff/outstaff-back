package ru.k2.outstaff.persistence.entity

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@NamedQuery(name = "Role.findAll",
        query = "select r from Role r where r.deleted <= :deleted")

@Entity
@Table(name = "roles")
class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    var id: Long? = null,

    @Column(name = "C_role_name")
    var roleName: String? = null,

    @Column(name = "d_date")
    @CreationTimestamp
    var date: LocalDateTime? = null,

    @Column(name = "n_delete")
    var deleted: Int = 0,

    @Column(name = "c_comment")
    var comment: String? = null,

    @OneToMany(mappedBy = "role")
    var userRoles: MutableList<UserRole>? = null
)