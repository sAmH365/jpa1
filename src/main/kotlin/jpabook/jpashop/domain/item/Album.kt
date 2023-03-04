package jpabook.jpashop.domain.item

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
@DiscriminatorValue("A")
class Album: Item() {

    var artist: String? = null
    var etc: String? = null
}