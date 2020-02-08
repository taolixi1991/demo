package com.wuhandata.wxncovblackboard.controller.admin

import com.wuhandata.wxncovblackboard.service.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @Autowired
    lateinit var service: SystemService

    @GetMapping("/")
    fun index(model: Model): String {
        val userDetail = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val modules = service.getModulesByUsername(userDetail.username)
        model.addAttribute("modules", modules)
        return "index.html"
    }

    @GetMapping("/dashboard.html")
    fun dashboard(): String {
        return "dashboard.html"
    }

    @GetMapping("/login.html")
    fun login(): String {
        return "login.html"
    }


}
