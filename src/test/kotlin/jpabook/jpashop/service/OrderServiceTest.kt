package jpabook.jpashop.service

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.exception.NotEnoughStockException
import jpabook.jpashop.repository.OrderRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalStateException

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext lateinit var em:EntityManager
    @Autowired lateinit var orderService: OrderService
    @Autowired lateinit var orderRepository: OrderRepository

    @Test
    fun 상품주문() {
        // given
        val member = createMember()

        val book = createBook("JPA활용1", 18000, 10)

        val orderCount = 2

        // when
        val orderId = orderService.order(member.id!!, book.id!!, orderCount)

        // then
        val getOrder = orderRepository.findOne(orderId)

        // 상품 주문시 상태는 ORDER
        Assertions.assertThat(getOrder.status).`as`("상품 주문시 상태는 ORDER").isEqualTo(OrderStatus.ORDER)
        // 주문한 상품 종류 수가 정확해야 한다
        Assertions.assertThat(getOrder.orderItems.size).`as`("주문한 상품 종류 수가 정확해야 한다").isEqualTo(1)
        // 주문 가격은 가격 * 수량이다
        Assertions.assertThat(getOrder.getTotalPrice()).`as`("주문 가격은 가격 * 수량이다").isEqualTo(18000 * orderCount)
        // 주문 수량만큼 재고가 줄어야 한다
        Assertions.assertThat(book.stockQuantity).`as`("주문 수량만큼 재고가 줄어야 한다").isEqualTo(8)
    }

    @Test
    fun 상품주문_재고수량_초과() {
        // given
        val member = createMember()
        val item = createBook("시골 JPA", 10000, 10)

        val orderCount = 11

        // when/then
        Assertions.assertThatThrownBy {
            orderService.order(member.id!!, item.id!!, orderCount)
        }.`as`("예외가 발생해야 한다")
            .isInstanceOf(NotEnoughStockException::class.java)
            .hasMessage("need more stock")
    }

    @Test
    fun 주문취소() {
        // given
        val member = createMember()
        val item = createBook("시골 JPA", 10000, 10)

        val orderCount = 2

        val orderId = orderService.order(member.id!!, item.id!!, orderCount)

        // when
        orderService.cancelOrder(orderId)

        // then
        val getOrder = orderRepository.findOne(orderId)

        Assertions.assertThat(getOrder.status).`as`("주문 취소시 상태는 CANCEL 이다.")
            .isEqualTo(OrderStatus.CANCEL)
        Assertions.assertThat(item.stockQuantity).`as`("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.")
            .isEqualTo(10)
    }

    private fun createBook(name: String, price: Int, stockQuantity: Int): Book {
        val book = Book()
        book.name = name
        book.price = price
        book.stockQuantity = stockQuantity
        em.persist(book)
        return book
    }

    private fun createMember(): Member {
        val member = Member()
        member.name = "회원1"
        member.address = Address(city = "서울", street = "가로수길", zipcode = "332-142")
        em.persist(member)
        return member
    }
}