package com.wuhandata.wxncovblackboard.controller.system

import com.wuhandata.wxncovblackboard.common.vo.DataTableParams
import com.wuhandata.wxncovblackboard.common.vo.Response
import com.wuhandata.wxncovblackboard.service.SystemService
import com.wuhandata.wxncovblackboard.domain.system.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.support.SessionStatus
import java.lang.Exception

@Controller
@RequestMapping("/system/user")
@SessionAttributes("user")
class UserController {

    @Autowired
    lateinit var service: SystemService

    val mapper = ObjectMapper()

    @GetMapping("/index")
    fun index(): String{
        return "system/user/index.html"
    }

    @ResponseBody
    @PostMapping("/index")
    fun getList(name: String, params: DataTableParams): String {
        val list = service.getUsers(name, "back", 0, params)
        list.draw = params.draw
        return mapper.writeValueAsString(list)
    }

    @GetMapping("/create")
    fun create(model: Model): String{
        model.addAttribute("user", User())
        return "system/user/save.html"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable("id")id: Long, model: Model): String{
        model.addAttribute("user", service.getUser(id))
        return "system/user/edit.html"
    }

    @ResponseBody
    @PostMapping("/save")
    fun save(@ModelAttribute("user")user: User, status: SessionStatus): String{
        user.type = "back"
        val response = try {
            service.saveUser(user)
            status.setComplete()
            Response(code = 0, msg = "success")
        }
        catch (e: Exception){
            Response(code = 1, msg = e.message)
        }
        return mapper.writeValueAsString(response)
    }

    @ResponseBody
    @PostMapping("/delete")
    fun delete(ids: String): String{
        val response = try {
            service.deleteUsers(ids)
            Response(code = 0, msg = "success")
        }
        catch (e: Exception){
            Response(code = 1, msg = e.message)
        }
        return mapper.writeValueAsString(response)
    }

    @RequestMapping(value = "/assign/{id}", method = [RequestMethod.GET])
    fun assign(@PathVariable("id") id: Long, model: Model): String {
        val user = service.getUser(id)
        val roles = service.getAllRolesWithUserSelected(user)
        model.addAttribute("user", user)
        model.addAttribute("roles", roles)
        return "system/user/assign.html"
    }

    @RequestMapping(value = "/assign", method = [RequestMethod.POST])
    @ResponseBody
    fun assignPermission(@RequestParam("userId") userId: Long, @RequestParam(value = "role", required = false) role: String?): String {
        val response = try {
            var role = role
            role = role ?: ""
            service.assignRolesToUser(userId, role)
            Response(code = 0, msg = "success")
        }
        catch (e: Exception){
            Response(code = 1, msg = e.message)
        }
        return mapper.writeValueAsString(response)
    }

}
