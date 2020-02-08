package com.wuhandata.wxncovblackboard.controller.system

import com.wuhandata.wxncovblackboard.common.vo.DataTableParams
import com.wuhandata.wxncovblackboard.common.vo.Response
import com.wuhandata.wxncovblackboard.service.SystemService
import com.wuhandata.wxncovblackboard.domain.system.Role
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.support.SessionStatus
import java.lang.Exception

@Controller
@RequestMapping("/system/role")
@SessionAttributes("role")
class RoleController {

    @Autowired
    lateinit var service: SystemService

    val mapper = ObjectMapper()

    @GetMapping("/index")
    fun index(): String{
        return "system/role/index.html"
    }

    @ResponseBody
    @PostMapping("/index")
    fun getList(params: DataTableParams): String {
        val list = service.getRoles(params)
        list.draw = params.draw
        return mapper.writeValueAsString(list)
    }

    @GetMapping("/create")
    fun create(model: Model): String{
        model.addAttribute("role", Role())
        return "system/role/save.html"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable("id")id: Long, model: Model): String{
        model.addAttribute("role", service.getRole(id))
        return "system/role/save.html"
    }

    @ResponseBody
    @PostMapping("/save")
    fun save(@ModelAttribute("role")role: Role, status: SessionStatus): String{
        val response = try {
            service.saveRole(role)
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
            service.deleteRoles(ids)
            Response(code = 0, msg = "success")
        }
        catch (e: Exception){
            Response(code = 1, msg = e.message)
        }
        return mapper.writeValueAsString(response)
    }

    @RequestMapping(value = "/assign/{id}", method = [RequestMethod.GET])
    fun assign(@PathVariable("id") id: Long, model: Model): String {
        val role = service.getRole(id)
        val permissions = service.getAllPermissionsWithRoleSelected(role)
        model.addAttribute("role", role)
        model.addAttribute("permissions", permissions)
        return "system/role/assign.html"
    }

    @RequestMapping(value = "/assign", method = [RequestMethod.POST])
    @ResponseBody
    fun assignPermission(@RequestParam("roleId") roleId: Long, @RequestParam(value = "permission", required = false) permission: String?): String {
        val response = try {
            var permission = permission
            permission = permission ?: ""
            service.assignPermissionsToRole(roleId, permission)
            Response(code = 0, msg = "success")
        }
        catch (e: Exception){
            Response(code = 1, msg = e.message)
        }
        return mapper.writeValueAsString(response)
    }

}
