package jpabook.jpashop.domain

import jakarta.persistence.*
import jpabook.jpashop.domain.item.Item

@Entity
class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    var id: Long? = null

    var name: String? = null

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
        )
    var items: MutableList<Item> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Category? = null

    @OneToMany(mappedBy = "parent")
    var child: MutableList<Category> = mutableListOf()

    fun addChildCategory(child: Category) {
        this.child.add(child)
        child.parent = this
    }
}