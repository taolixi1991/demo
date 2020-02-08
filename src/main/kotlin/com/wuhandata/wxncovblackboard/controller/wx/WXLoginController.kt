package com.wuhandata.wxncovblackboard.controller.wx

import com.alibaba.fastjson.JSONObject
import com.wuhandata.wxncovblackboard.common.utils.AesHelper
import com.wuhandata.wxncovblackboard.common.utils.RequestUtil
import com.wuhandata.wxncovblackboard.common.vo.AuthRequest
import com.wuhandata.wxncovblackboard.common.vo.Response
import com.wuhandata.wxncovblackboard.service.CustomerService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wxmp")
class WXLoginController {
    val log = LoggerFactory.getLogger(this.javaClass)

    @Value("\${wx.blackboard.appId}")
    private val appId: String = ""

    @Value("\${wx.blackboard.appSecret}")
    private val appSec: String = ""

    @Autowired
    lateinit var customerService: CustomerService

    companion object{
        val CODE_TO_SESSION_URL_BASE = "https://api.weixin.qq.com/sns/jscode2session?"
    }

    /**
     *  1. 通过code向微信服务端获取openId, unionId, sessionKey,并保存
     *  3. 根据用户是否有手机号，判断是否为新用户
     */
    @PostMapping("/initLogin")
    @ResponseBody
    fun initWechatLogin(@RequestBody wechatCode: String):Response {
        val response = Response()
        if(!StringUtils.isEmpty(wechatCode)) {
            val requestUrl = getCode2SessionRequestUrl(wechatCode)
            val responseBody = RequestUtil.getInSSLUnsafe(requestUrl)
            if (!StringUtils.isEmpty(responseBody)) {
                val body = JSONObject.parse(responseBody) as JSONObject
                if (body.containsKey("errcode") || body.containsKey("errmsg")) {
                    response.code = 1
                    response.msg = body["errmsg"] as String
                } else {
                    val customer = customerService.getCustomerByOpenId(body["openid"] as String)
                    val customerId = if (customer == null) {
                        customerService.saveNewCustomer(body)
                    } else {
                        customer.sessionKey = body["session_key"] as String
                        customerService.updateExistingCustomer(customer)
                    }

                    if (!StringUtils.isEmpty(customerId)) {
                        response.code = 0
                        response.data = customerService.getCustomer(customerId)
                    } else {
                        response.code = 1
                        response.msg = "新建用户失败"
                    }
                }
            }
        }
        return response
    }

    /**
     *  1. 根据openId查询用户sessionKey
     *  2. 根据sessionKey,iv对encryptedData进行解密，详情：https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/signature.html#%E5%8A%A0%E5%AF%86%E6%95%B0%E6%8D%AE%E8%A7%A3%E5%AF%86%E7%AE%97%E6%B3%95
     *  3. 成功，保存手机号
     *  4. 失败，返回失败信息
     */
    @PostMapping("/authByWechat")
    @ResponseBody
    fun authUserByWechatInfo(@RequestBody request: AuthRequest): Response {
        var response = Response()
        val openId = request.openId
        if(!StringUtils.isEmpty(openId)) {
            val customer = customerService.getCustomerByOpenId(openId)
            if(customer != null) {
                if(!StringUtils.isEmpty(customer.phone)) {
                    response.code = 1
                    response.msg = "已完成用户注册，请勿重新提交"
                    return response
                }
                val encryptedData = request.encryptedData
                val responseStr = AesHelper.decryptForWeChatData(encryptedData, customer.sessionKey, request.iv)
                val responseBody = JSONObject.parse(responseStr) as JSONObject
                if(isCorrectWithWatermark(responseBody.getString("watermark"))) {
                    val customerId = customerService.authExistingCustomer(openId, responseBody.getString("purePhoneNumber"), responseBody.getString("countryCode"))
                    if(!StringUtils.isEmpty(customerId)) {
                        response.code = 0
                        response.data = responseBody
                    } else {
                        response.code = 1
                        response.msg = "用户保存失败"
                    }
                } else {
                    response.code = 1
                    response.msg = "无效code"
                }
            } else {
                response.code = 1
                response.msg = "该用户不存在"
            }
        }
        return response;
    }

    private fun getCode2SessionRequestUrl(code: String): String {
        return CODE_TO_SESSION_URL_BASE + "appid=" + appId + "&secret=" + appSec + "&js_code=" + code + "&grant_type=authorization_code"
    }

    private fun isCorrectWithWatermark(watermark: String): Boolean {
        if(watermark.contains(appId)) {
            return true
        }
        return false
    }
}
