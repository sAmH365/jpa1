package jpabook.jpashop.service

import jpabook.jpashop.domain.Delivery
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.repository.ItemRepository
import jpabook.jpashop.repository.MemberRepository
import jpabook.jpashop.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService(
    val orderRepository: OrderRepository,
    val memberRepository: MemberRepository,
    val itemRepository: ItemRepository,
) {

    /**
     * 주문
     */
    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long {
        // 엔티티 조회
        val member = memberRepository.findOne(id = memberId)
        val item = itemRepository.findOne(itemId)

        // 배송정보 생성
        val delivery = Delivery()
        delivery.address = member.address

        // 주문상품 생성
        val orderItem = OrderItem.createOrderItem(item, item.price!!, count)

        // 주문 생성
        val order = Order.createOrder(member, delivery, orderItem)

        // 주문 저장
        orderRepository.save(order)

        return order.id!!
    }

    /**
     * 주문 취소
     */
    @Transactional
    fun cancelOrder(id: Long) {
        // 주문 엔티티 조회
        val order = orderRepository.findOne(id)
        // 주문 취소
        order.cancel()
    }

    // 검색
/*    fun findOrders(orderSearch: OrderSearch): MutableList<Order> {
        return orderRepository.findAll(orderSearch)
    }*/
}