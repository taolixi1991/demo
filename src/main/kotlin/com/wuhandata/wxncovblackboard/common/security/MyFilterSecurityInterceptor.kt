package com.wuhandata.wxncovblackboard.common.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.SecurityMetadataSource
import org.springframework.security.access.intercept.AbstractSecurityInterceptor
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource
import org.springframework.stereotype.Service
import javax.servlet.*

@Service
class MyFilterSecurityInterceptor: AbstractSecurityInterceptor(), Filter {

    @Autowired
    private lateinit var securityMetadataSource: FilterInvocationSecurityMetadataSource

    @Autowired
    fun setMyAccessDecisionManager(myAccessDecisionManager: MyAccessDecisionManager){
        super.setAccessDecisionManager(myAccessDecisionManager)
    }

    override fun init(filterConfig: FilterConfig?) {

    }

    override fun getSecureObjectClass(): Class<*> {
        return FilterInvocation::class.java
    }

    override fun obtainSecurityMetadataSource(): SecurityMetadataSource {
        return this.securityMetadataSource
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val fi = FilterInvocation(request, response, chain)
        invoke(fi)
    }

    override fun destroy() {

    }

    fun invoke(fi: FilterInvocation){
        val token = super.beforeInvocation(fi)
        try{
            fi.chain.doFilter(fi.request, fi.response)
        } finally {
            super.afterInvocation(token, null)
        }
    }

}
