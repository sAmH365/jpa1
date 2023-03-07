package jpabook.jpashop.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jpabook.jpashop.domain.Order
import org.springframework.stereotype.Repository

@Repository
class OrderRepository {

    @PersistenceContext
    lateinit var em: EntityManager

    fun save(order: Order) {
        em.persist(order)
    }

    fun findOne(id: Long?): Order {
        return em.find(Order::class.java, id)
    }

    fun findAll(orderSearch: OrderSearch): MutableList<Order> {

        return em.createQuery("select o from Order o join o.member m where o.status = :status and m.name like :name"
            , Order::class.java)
            .setParameter("status", orderSearch.orderStatus)
            .setParameter("name", orderSearch.memberName)
            .setMaxResults(1000)
            .resultList
    }
}