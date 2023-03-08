package jpabook.jpashop.controller

import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

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
}