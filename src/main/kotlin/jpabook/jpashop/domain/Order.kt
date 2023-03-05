package jpabook.jpashop.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "orders")
class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: MutableList<OrderItem> = mutableListOf()

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id ")
    var delivery: Delivery? = null

    var orderDate: LocalDateTime? = null

    @Enumerated(EnumType.STRING)
    var status: OrderStatus? = null

    // 연관관계 메서드
    fun setMember(member: Member) {
        this.member = member
        member.orders.add(this)
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    fun setDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }
}