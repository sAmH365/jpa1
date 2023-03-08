package jpabook.jpashop.controller

import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import java.lang.IllegalArgumentException

@Controller
class ItemController(
    val itemService: ItemService
) {

    @GetMapping("/items/new")
    fun createForm(model: Model): String {
        model.addAttribute("form", BookForm())
        return "items/createItemForm"
    }

    @PostMapping("/items/new")
    fun create(form: BookForm): String {
        val book = Book()
        book.name = form.name
        book.price = form.price
        book.stockQuantity = form.stockQuantity
        book.author = form.author
        book.isbn = form.isbn

        itemService.saveItem(book)
        return "redirect:/items"
    }

    @GetMapping("/items")
    fun list(model: Model): String {
        val items = itemService.findItems()
        model.addAttribute("items", items)
        return "/items/itemList"
    }

    @GetMapping("/items/{itemId}/edit")
    fun updateItemForm(@PathVariable("itemId") itemId: Long, model: Model): String {
        val item = itemService.findOne(itemId) as Book

        val form = BookForm()
        form.id = item.id!!
        form.name = item.name!!
        form.price = item.price!!
        form.stockQuantity = item.stockQuantity!!
        form.author = item.author!!
        form.isbn = item.isbn!!

        model.addAttribute("form", form)
        return "items/updateItemForm"
    }

    @PostMapping("/items/{itemId}/edit")
    fun updateItemForm(@ModelAttribute("form") form:BookForm): String {

        val book = Book()
        book.id = form.id
        book.name = form.name
        book.price = form.price
        book.stockQuantity = form.stockQuantity
        book.author = form.author
        book.isbn = form.isbn

        itemService.saveItem(book)
        return "redirect:/items"
    }
}