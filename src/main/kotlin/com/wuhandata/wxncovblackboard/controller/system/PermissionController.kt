package com.wuhandata.wxncovblackboard.controller.system

import com.wuhandata.wxncovblackboard.common.vo.Response
import com.wuhandata.wxncovblackboard.common.vo.TreeNode
import com.wuhandata.wxncovblackboard.service.SystemService
import com.wuhandata.wxncovblackboard.domain.system.Permission
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.support.SessionStatus
import java.lang.Exception

@Controller
@RequestMapping("/system/permission")
@SessionAttributes("permission")
class PermissionController {

    @Autowired
    lateinit var service: SystemService

    val mapper = ObjectMapper()

    @GetMapping("/index")
    fun index(): String{
        return "system/permission/index.html"
    }

    @ResponseBody
    @PostMapping("/index")
    fun getList(@RequestParam("pid")pid: String): String {
        var id = pid
        if(pid.isEmpty()) id = "0"
        return mapper.writeValueAsString(service.getPermissions(id.toLong(), "PERMISSION_TYPE_ACTION"))
    }

    @ResponseBody
    @GetMapping("/tree")
    fun tree(@RequestParam("pid")pid: String): List<TreeNode>{
        var id = pid
        if (pid == "#") id = "0"
        return service.getPermissionModuleTreesByParentId(id.toLong())
    }

    @GetMapping("/create")
    fun create(pid: String, type: String, model: Model): String{
        val permission = Permission()
        permission.parentId = pid
        permission.type = type
        model.addAttribute("permission", permission)
        return "system/permission/save.html"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable("id")id: Long, model: Model): String{
        model.addAttribute("permission", service.getPermission(id))
        return "system/permission/save.html"
    }

    @ResponseBody
    @PostMapping("/save")
    fun save(@ModelAttribute("permission")permission: Permission, status: SessionStatus): String{
        val response = try {
            service.savePermission(permission)
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
            service.deletePermissions(ids)
            Response(code = 0, msg = "success")
        }
        catch (e: Exception){
            Response(code = 1, msg = e.message)
        }
        return mapper.writeValueAsString(response)
    }

}
