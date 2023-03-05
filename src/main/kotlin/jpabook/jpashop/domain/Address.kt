package jpabook.jpashop.domain

import jakarta.persistence.Embeddable

@Embeddable
class Address(
    var city: String?,
    var street: String?,
    var zipcode: String?
)