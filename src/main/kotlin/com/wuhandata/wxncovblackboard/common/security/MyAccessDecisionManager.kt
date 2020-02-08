package com.wuhandata.wxncovblackboard.common.security

import org.springframework.security.access.AccessDecisionManager
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class MyAccessDecisionManager: AccessDecisionManager {

    override fun decide(authentication: Authentication?, `object`: Any?, configAttributes: MutableCollection<ConfigAttribute>?) {
        if(configAttributes == null || configAttributes.isEmpty())
            return

        var needRole: String
        configAttributes.iterator().forEach {
            needRole = it.attribute
            for(ga in authentication!!.authorities){
                if(needRole.trim() == ga.authority)
                    return
            }
        }
        throw AccessDeniedException("没有权限")
    }

    override fun supports(attribute: ConfigAttribute?): Boolean {
        return true
    }

    override fun supports(clazz: Class<*>?): Boolean {
        return true
    }

}
