package jpabook.jpashop.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {

    val log:Logger = LoggerFactory.getLogger(HomeController::class.java)

    @RequestMapping("/")
    fun home(): String {
        log.info("home controller")
        return "home"
    }
}