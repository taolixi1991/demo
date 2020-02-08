package com.wuhandata.wxncovblackboard.controller.wx

import com.wuhandata.wxncovblackboard.service.SpinnerOptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wxmp/filter")
class WXOptionController {

    @Autowired
    lateinit var optionService: SpinnerOptionService
//    /**
//     * 获取搜索条件中的全部经营项目
//     */
//    @GetMapping("/projects")
//    fun getAllProjects(): Response {
//        var response = Response()
//        response.data= optionService.findAllAndChildByType(type="project")
//        return response
//    }
//
//    /**
//     * 获取搜索条件中的门店类型
//     */
//    @GetMapping("/storeTypes")
//    fun getAllStoreTypes(): Response {
//        var response = Response()
//        response.data= optionService.findAllAndChildByType(type="storeType")
//        return response
//    }
}
