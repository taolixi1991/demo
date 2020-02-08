package com.wuhandata.wxncovblackboard.domain

import javax.persistence.Entity
import javax.persistence.Table

/**
 *  微信账号，唯一识别ID为openId
 */
@Entity
@Table(name = "t_customer")
class Customer (
        var openId: String = "",
        var sessionKey: String = "",
        var unionId: String = "",
        var phone: String = "",
        var countryCode: String = "",
        var email: String = ""
): BaseDomain()
