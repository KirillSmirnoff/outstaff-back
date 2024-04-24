package ru.k2.outstaff.repository.entity

import ru.k2.outstaff.utils.WorkerType
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "workers")
class Worker(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "n_id")
        var id: Long? = null,

        @Column(name = "n_name")
        var name: String? = null,

        @Column(name = "d_birthday")
        var birthday: LocalDate? = null,

        @Column(name = "c_phone")
        var phone: String? = null,

        @Column(name = "c_mail")
        var mail: String? = null,

        @Column(name = "n_type")
        @Enumerated(EnumType.STRING)
        var type: WorkerType? = null,

        @ManyToOne(optional = false)
        @JoinColumn(name = "n_company_id")
        var company: Company? = null,
)