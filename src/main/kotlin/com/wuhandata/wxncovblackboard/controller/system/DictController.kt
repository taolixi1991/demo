package com.wuhandata.wxncovblackboard.controller.system

import com.wuhandata.wxncovblackboard.common.vo.DataTableParams
import com.wuhandata.wxncovblackboard.common.vo.Response
import com.wuhandata.wxncovblackboard.common.vo.TreeNode
import com.wuhandata.wxncovblackboard.service.SystemService
import com.wuhandata.wxncovblackboard.domain.system.Dict
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.support.SessionStatus
import java.lang.Exception

@Controller
@RequestMapping("/system/dict")
@SessionAttributes("dict")
class DictController {

    @Autowired
    lateinit var service: SystemService

    val mapper = ObjectMapper()

    @GetMapping("/index")
    fun index(): String{
        return "system/dict/index.html"
    }

    @ResponseBody
    @PostMapping("/index")
    fun getList(@RequestParam("pid")pid: String, params: DataTableParams): String {
        var id = pid
        if(pid.isEmpty()) id = "0"
        return mapper.writeValueAsString(service.getDicts(id.toLong(), params))
    }

    @ResponseBody
    @GetMapping("/tree")
    fun tree(@RequestParam("pid")pid: String): List<TreeNode>{
        var id = pid
        if (pid == "#") id = "0"
        return service.getDictTreesByParentId(id.toLong())
    }

    @GetMapping("/create")
    fun create(@RequestParam("pid")pid: String,model: Model): String{
        val dict = Dict()
        var id = pid
        if(pid.isEmpty()) id = "0"
        dict.parentId = id.toLong()
        model.addAttribute("dict", dict)
        return "system/dict/save.html"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable("id")id: Long, model: Model): String{
        model.addAttribute("dict", service.getDict(id))
        return "system/dict/save.html"
    }

    @ResponseBody
    @PostMapping("/save")
    fun save(@ModelAttribute("dict")dict: Dict, status: SessionStatus): String{
        val response = try {
            service.saveDict(dict)
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
            service.deleteDicts(ids)
            Response(code = 0, msg = "success")
        }
        catch (e: Exception){
            Response(code = 1, msg = e.message)
        }
        return mapper.writeValueAsString(response)
    }

}
