package jpabook.jpashop.domain.item

import jakarta.persistence.*
import jpabook.jpashop.AllOpen
import jpabook.jpashop.domain.Category
import jpabook.jpashop.exception.NotEnoughStockException

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@AllOpen
abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    var id: Long? = null

    var name: String? = null
    var price: Int? = null
    var stockQuantity: Int? = null

    @ManyToMany(mappedBy = "items")
    val categories: MutableList<Category> = mutableListOf()

    // 비즈니스 로직

    /**
     * stock 증가
     */
    fun addStock(quantity: Int) {
        this.stockQuantity = this.stockQuantity?.plus(quantity)
    }

    /**
     * stock 감소
     */
    fun removeStock(quantity: Int) {
        val restStock = this.stockQuantity?.minus(quantity)
        if (restStock != null) {
            if (restStock < 0) {
                throw NotEnoughStockException("need more stock")
            }
        }
        this.stockQuantity = restStock
    }
}
