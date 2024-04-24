package ru.k2.outstaff.repository.entity

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "roles")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "Cached-Roles")
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

    @Column(name = "c_comment")
    var comment: String? = null,

    @OneToMany(mappedBy = "role")
    var userRoles: MutableList<UserRole>? = null
)