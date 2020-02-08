package com.wuhandata.wxncovblackboard.service

import com.alibaba.fastjson.JSONObject
import com.wuhandata.wxncovblackboard.common.utils.Helper
import com.wuhandata.wxncovblackboard.common.vo.DataTable
import com.wuhandata.wxncovblackboard.common.vo.DataTableParams
import com.wuhandata.wxncovblackboard.domain.Customer
import com.wuhandata.wxncovblackboard.repo.CustomerRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerService {
    protected val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var customerRepo: CustomerRepository

    fun saveNewCustomer(body: JSONObject): String {
        val customer = Customer()
        customer.openId = body["openid"] as String
        customer.sessionKey = body["session_key"] as String
        if(body.containsKey("unionid")) {
            customer.unionId = body["unionid"] as String
        }
        var customerId = ""
        try {
            customerId = customerRepo.saveAndFlush(customer).id
        } catch (e: Exception) {
            log.error(e.message)
        }
        log.info("用户id: $customerId 创建成功")
        return customerId
    }

    fun updateExistingCustomer(customer: Customer): String {
        try {
            customerRepo.saveAndFlush(customer)
            return customer.id
        } catch (e: Exception) {
            log.error(e.message)
        }
        log.info("用户id: ${customer.id} 更新成功")
        return ""
    }

    fun authExistingCustomer(openId: String, phoneNumber: String, countryCode: String): String {
        val customer = customerRepo.findByOpenId(openId).get()
        customer.phone = phoneNumber
        customer.countryCode = countryCode
        try {
            customerRepo.saveAndFlush(customer)
            return customer.id
        } catch (e: Exception) {
            log.error(e.message)
        }
        log.info("用户id: ${customer.id} 与手机号码: "+countryCode +" " + phoneNumber + " 绑定成功")
        return ""
    }

    fun getCustomerByOpenId(openId: String): Customer? {
        var customer:Customer? = null
        try {
            val optional = customerRepo.findByOpenId(openId)
            if(optional.isPresent) {
                customer = optional.get()
            }
        } catch (e: Exception) {
            log.error(e.message)
        }

        return customer
    }

    fun getCustomerPublicInfo(openId: String): Customer? {
        var customer: Customer? = null
        try {
            val optional = customerRepo.findByOpenId(openId)
            if(optional.isPresent) {
                customer = optional.get()
                encryptCustomer(customer)
            }
        } catch (e: Exception) {
            log.error(e.message)
        }
        return customer
    }

    fun getCustomersDataTable(params: DataTableParams): DataTable<Customer> {
        val pageable = Helper.makePage(params)
        val list = customerRepo.findAll(pageable)
        val dt = DataTable<Customer>()
        dt.data = list.content
        dt.recordsFiltered = list.totalElements
        dt.recordsTotal = list.totalElements
        return dt
    }

    fun getCustomer(id: String): Customer? {
        var customer: Customer? = null
        try {
            val optional = customerRepo.findById(id)
            if(optional.isPresent) {
                customer = optional.get()
            }
        } catch (e: Exception) {
            log.error(e.message)
        }
        return customer
    }

    fun encryptCustomer(customer: Customer) {
        customer.id = ""
        customer.sessionKey = ""
        customer.unionId = ""
    }

}
