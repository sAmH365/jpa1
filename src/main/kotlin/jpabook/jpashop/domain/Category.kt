package jpabook.jpashop.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
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
}