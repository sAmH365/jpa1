package jpabook.jpashop.domain

import jakarta.persistence.*
import jpabook.jpashop.domain.item.Item

@Entity
class OrderItem {

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

    var orderPrice: Int? = null // 주문가격
    var count: Int? = null // 주문수량
}
