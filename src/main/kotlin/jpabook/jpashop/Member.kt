package jpabook.jpashop

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Member {

    @Id
    @GeneratedValue
    var id: Long? = null
    var username: String? = null
}