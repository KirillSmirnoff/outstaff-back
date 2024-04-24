package ru.k2.outstaff.repository.entity

import javax.persistence.*

@Entity
@Table(name = "companies")
class Company(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "n_id")
        var id: Long? = null,

        @Column(name = "c_company_name")
        var companyName: String? = null,

        @Column(name = "c_additonal")
        var additional: String? = null
)

