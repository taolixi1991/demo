package com.wuhandata.wxncovblackboard.controller.admin

import com.fasterxml.jackson.databind.ObjectMapper
import com.wuhandata.wxncovblackboard.common.vo.DataTableParams
import com.wuhandata.wxncovblackboard.common.vo.Response
import com.wuhandata.wxncovblackboard.common.vo.TreeNode
import com.wuhandata.wxncovblackboard.domain.system.SpinnerOption
import com.wuhandata.wxncovblackboard.service.SpinnerOptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@Controller
@RequestMapping("/option")
class OptionController {
    @Autowired
    lateinit var service: SpinnerOptionService

    val mapper = ObjectMapper()

    @GetMapping("/index")
    fun optionIndex(): String {
        return "option/index.html"
    }

    @ResponseBody
    @GetMapping("/tree")
    fun tree(@RequestParam("pid")pid: String): List<TreeNode>{
        var id = pid
        if (pid == "#") id = ""
        return getDictTreesByParentId("project",id)
    }

    @ResponseBody
    @GetMapping("/tree2")
    fun tree2(): List<TreeNode>{
        return getDictTreesByParentId("storeType","")
    }

    @GetMapping("/create/{pid}")
    fun create(@PathVariable("pid")pid: String,@RequestParam("type")type: String,model: Model): String {
        val option = SpinnerOption()
        var id=pid
        if (pid.equals("undefined")){
            id=""
        }
        option.parentId=id
        option.type=type
        model.addAttribute("option",option)
        return "option/save.html"
    }

    fun getDictTreesByParentId(type:String,id: String): List<TreeNode> {
        val list = service.findAllByTypeAndParentId(type,id)
        val result: MutableList<TreeNode> = ArrayList()
        for(dict in list){
            val node = TreeNode()
            node.id = dict.id.toString()
            node.text = dict.name
            if (service.findAllByParentId(dict.id.toString()).isNotEmpty())
                node.children = true
            result.add(node)
        }
        return result
    }

    @ResponseBody
    @PostMapping("/index")
    fun getListByType(type: String, params: DataTableParams): String{
        return ""
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable("id")id: Long, model: Model): String{
        model.addAttribute("option",service.findById(id))
        return "option/save.html"
    }


    /**
     * 两类：1. 经营项目（project），包含两级菜单，父级parentId为空，2.门店类型（storeType)，为一级菜单，parentId仅为空
     */
    @ResponseBody
    @PostMapping("/save")
    fun save(@ModelAttribute("option")option: SpinnerOption): String{
        val response = try {
            if (0L!=option.id){
                option.version= service.findById(option.id).version
            }
            service.save(option)
            Response(code = 0, msg = "success")
        }
        catch (e: Exception){
            e.printStackTrace()
            Response(code = 1, msg = e.message)
        }
        return mapper.writeValueAsString(response)
    }

    @ResponseBody
    @PostMapping("/delete")
    fun delete(id: Long): String{
        val response = try {
            service.delete(id)
            Response(code = 0, msg = "success")
        }
        catch (e: Exception){
            Response(code = 1, msg = e.message)
        }
        return mapper.writeValueAsString(response)
    }


}
