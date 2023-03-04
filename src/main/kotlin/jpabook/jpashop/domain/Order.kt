package jpabook.jpashop.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "orders")
class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "member_id")
    var member: Member? = null

    @OneToMany(mappedBy = "order")
    var orderItems: MutableList<OrderItem>? = mutableListOf()

    @OneToOne
    @JoinColumn(name = "delivery_id ")
    var delivery: Delivery? = null

    var orderDate: LocalDateTime? = null

    @Enumerated(EnumType.STRING)
    var status: OrderStatus? = null
}