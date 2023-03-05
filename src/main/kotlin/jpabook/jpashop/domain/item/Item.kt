package jpabook.jpashop.domain.item

import jakarta.persistence.*
import jpabook.jpashop.AllOpen
import jpabook.jpashop.domain.Category

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
}
