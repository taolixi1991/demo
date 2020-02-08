package com.wuhandata.wxncovblackboard.controller.admin

import com.fasterxml.jackson.databind.ObjectMapper
import com.wuhandata.wxncovblackboard.common.vo.DataTableParams
import com.wuhandata.wxncovblackboard.domain.Customer
import com.wuhandata.wxncovblackboard.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/account")
@SessionAttributes("user")
class AccountController {

    @Autowired
    lateinit var customerService: CustomerService

    val mapper = ObjectMapper()

    @GetMapping("/index")
    fun index(): String{
        return "account/index.html"
    }

    @ResponseBody
    @PostMapping("/index")
    fun getList(params: DataTableParams): String {
        val list = customerService.getCustomersDataTable(params)
        list.draw = params.draw
        return mapper.writeValueAsString(list)
    }

    @GetMapping("/create")
    fun create(model: Model): String{
        model.addAttribute("customer", Customer())
        return "account/save.html"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable("id")id: String, model: Model): String{
        model.addAttribute("customer", customerService.getCustomer(id))
        return "account/edit.html"
    }
}
