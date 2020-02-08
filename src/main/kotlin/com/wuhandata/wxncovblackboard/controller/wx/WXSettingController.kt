package com.wuhandata.wxncovblackboard.controller.wx

import com.wuhandata.wxncovblackboard.common.vo.Response
import com.wuhandata.wxncovblackboard.service.AttachmentService
import com.wuhandata.wxncovblackboard.domain.Customer
import com.wuhandata.wxncovblackboard.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wxmp/setting")
class WXSettingController {

    @Autowired
    lateinit var customerService: CustomerService

    @Autowired
    lateinit var attachmentService: AttachmentService

    /**
     * 根据openID获取用户信息
     */
    @GetMapping("/info")
    fun getPersonalInfo(openId: String):Response {
        val response =  Response()
        val info = customerService.getCustomerPublicInfo(openId)
        if(info != null) {
            response.code = 0
            response.data = info
        } else {
            response.code = 1
            response.msg = "无法找到该用户的信息"
        }
        return response
    }

    @PostMapping("/update")
    @ResponseBody
    fun updateEmail(@RequestBody customer: Customer): Response {
        val response = Response()
        val existingCustomer = customerService.getCustomerByOpenId(customer.openId)
        if(existingCustomer != null) {
            existingCustomer.email = customer.email
            val cid = customerService.updateExistingCustomer(existingCustomer)
            if(StringUtils.isEmpty(cid)) {
                response.code = 1
                response.msg = "更新用户信息失败"
            } else {
                response.code = 0
                response.msg = "更新成功"
            }
        } else {
            response.code = 1
            response.msg = "无法找到该用户的信息"
        }
        return response
    }

    @GetMapping("/slider")
    fun getSliders(): Response {
        val response = Response()
        response.code = 0
        response.data = attachmentService.getMainPageSliders()
        return response
    }


}
