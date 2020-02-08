package com.wuhandata.wxncovblackboard.common.security

import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
class UserAuditorBean: AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        var name = SecurityContextHolder.getContext().authentication.name
        name = if (name ==null) "" else name
        return Optional.ofNullable(name)
    }

}
