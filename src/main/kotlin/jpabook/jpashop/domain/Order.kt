package jpabook.jpashop.domain

import jakarta.persistence.*
import jpabook.jpashop.AllOpen
import java.time.LocalDateTime

@Entity(name = "orders")
@AllOpen
class Order {

    protected constructor()

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private var member: Member? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: MutableList<OrderItem> = mutableListOf()

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id ")
    private var delivery: Delivery? = null

    var orderDate: LocalDateTime? = null

    @Enumerated(EnumType.STRING)
    var status: OrderStatus? = null

    // 연관관계 메서드
    fun setMember(member: Member) {
        this.member = member
        member.orders.add(this)
    }

    private fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    fun setDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }

    // 비즈니스 로직
    /**
     * 주문 취소
     */
    fun cancel() {
        if (delivery?.status == DeliveryStatus.COMP) throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")

        this.status = OrderStatus.CANCEL
        for (orderItem in this.orderItems) {
            orderItem.cancel()
        }
    }

    // 조회 로직
    /**
     * 전체 주문 가격 조회
     */
    fun getTotalPrice(): Int {
       var totalPrice = 0
        for (orderItem in orderItems) {
            totalPrice += orderItem.getTotalPrice()
        }
        return totalPrice
    }

    companion object {
        // 생성 메서드
        fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem):Order {
            val order = Order()
            order.member = member
            order.delivery = delivery
            for (orderItem in orderItems) {
                order.addOrderItem(orderItem)
            }
            order.status = OrderStatus.ORDER
            order.orderDate = LocalDateTime.now()
            return order
        }
    }
}