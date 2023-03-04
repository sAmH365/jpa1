package jpabook.jpashop.domain.item

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
@DiscriminatorValue("M")
class Movie: Item() {

    var director: String? = null
    var actor: String? = null
}