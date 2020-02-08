package com.wuhandata.wxncovblackboard.common.security

import com.wuhandata.wxncovblackboard.service.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class CustomUserService: UserDetailsService {

    @Autowired
    lateinit var service: SystemService

    override fun loadUserByUsername(username: String): UserDetails {
        val user = service.getUserWithPermissions(username)
        if (!StringUtils.isEmpty(user.id)) {
            if (user.accountNonExpired) throw Exception("用户 $username 已过期！")
            if (user.accountNonLocked) throw Exception("用户 $username 被锁定！")
            if (user.credentialsNonExpired) throw Exception("用户 $username 已过期！")
            val permissions = user.permissions
            val grantedAuthorities = ArrayList<GrantedAuthority>()
            for (permission in permissions) {
                if (permission.name != "") {
                    val grantedAuthority = SimpleGrantedAuthority(permission.mask)
                    grantedAuthorities.add(grantedAuthority)
                }
            }
            return User(user.username, user.password, grantedAuthorities)
        }
        else
            throw UsernameNotFoundException("用户 $username 不存在！")
    }

}
