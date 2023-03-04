package jpabook.jpashop.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Embeddable
class Address {

    var city: String? = null
    var street: String? = null
    var zipcode: String? = null
}