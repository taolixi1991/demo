package com.wuhandata.wxncovblackboard.controller.admin

import com.fasterxml.jackson.databind.ObjectMapper
import com.wuhandata.wxncovblackboard.common.vo.Select
import com.wuhandata.wxncovblackboard.service.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/common")
class CommonController {

    @Autowired
    lateinit var sys: SystemService

    val mapper = ObjectMapper()

    @RequestMapping("/getDict")
    @ResponseBody
    fun getDict(@RequestParam("mask")mask: String): String{
        val dicts = sys.getDictsByParentMask(mask)
        val selects: MutableList<Select> = ArrayList()
        for(dict in dicts){
            val select = Select()
            select.id = dict.mask
            select.text = dict.name
            selects.add(select)
        }
        return mapper.writeValueAsString(selects)
    }

    @RequestMapping("/getNewDict")
    @ResponseBody
    fun getNewDict(@RequestParam("mask")mask: String): String{
        val dicts = sys.getDictsByParentMask(mask)
        val selects: MutableList<Select> = ArrayList()
        for(dict in dicts){
            val select = Select()
            select.id = dict.id.toString()
            select.text = dict.name
            selects.add(select)
        }
        return mapper.writeValueAsString(selects)
    }


}
