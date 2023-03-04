package jpabook.jpashop.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null

    var name: String? = null

    @Embedded
    var address: Address? = null

    @OneToMany(mappedBy = "member")
    var orders: MutableList<Order>? = mutableListOf()
}