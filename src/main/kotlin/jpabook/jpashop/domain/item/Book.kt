package jpabook.jpashop.domain.item

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
@DiscriminatorValue("B")
class Book: Item() {

    var author: String? = null
    var isbn: String? = null
}