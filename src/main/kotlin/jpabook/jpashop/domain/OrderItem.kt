package jpabook.jpashop.domain

import jakarta.persistence.*
import jpabook.jpashop.domain.item.Item

@Entity
open class OrderItem {

    protected constructor()

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null

    var orderPrice: Int = 0 // 주문가격
    var count: Int = 0 // 주문수량

    // 비즈니스 로직
    fun cancel() {
        this.item?.addStock(count)
    }

    fun getTotalPrice(): Int {
        return orderPrice * count
    }

    companion object {
        // 생성 메서드
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            val orderItem = OrderItem()
            orderItem.item = item
            orderItem.orderPrice = orderPrice
            orderItem.count = count

            item.removeStock(count)
            return orderItem
        }
    }
}
