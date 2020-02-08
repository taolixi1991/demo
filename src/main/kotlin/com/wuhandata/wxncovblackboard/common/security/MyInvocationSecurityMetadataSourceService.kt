package com.wuhandata.wxncovblackboard.common.security

import com.wuhandata.wxncovblackboard.service.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.access.SecurityConfig
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Service

@Service
class MyInvocationSecurityMetadataSourceService: FilterInvocationSecurityMetadataSource {

    @Autowired
    lateinit var service: SystemService

    var map: HashMap<String, MutableCollection<ConfigAttribute>>? = null

    override fun getAllConfigAttributes(): MutableCollection<ConfigAttribute>? {
        return null
    }

    override fun supports(clazz: Class<*>?): Boolean {
        return true
    }

    override fun getAttributes(`object`: Any?): MutableCollection<ConfigAttribute>? {
        if (map == null) loadResourceDefine()
        val request = (`object` as FilterInvocation).httpRequest
        var matcher: AntPathRequestMatcher
        map!!.keys.iterator().forEach {
            matcher = AntPathRequestMatcher(it)
            if(matcher.matches(request))
                return map!![it]
        }
        return null
    }

    fun loadResourceDefine(){
        map = HashMap()
        var array: Collection<ConfigAttribute>
        var cfg: ConfigAttribute
        val permissions = service.getAllPermissions()
        for(permission in permissions) {
            array = mutableListOf()
            cfg = SecurityConfig(permission.mask)
            array.add(cfg)
            map!![permission.url] = array
        }
    }

}
