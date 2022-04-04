package com.ig.importexel.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Employee(
    @Id
    var id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var address: String? = null,
    var phone: String? =  null
)
