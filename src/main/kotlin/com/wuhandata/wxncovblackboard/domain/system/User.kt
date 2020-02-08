package com.wuhandata.wxncovblackboard.domain.system

import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "sys_user")
class User(
        var username: String = "",
        var password: String = "",
        var enabled: Boolean = true,
        var accountNonExpired: Boolean = false,
        var accountNonLocked: Boolean = false,
        var credentialsNonExpired: Boolean = false,
        var fullName: String = "",
        var email: String = "",
        var mobile: String = "",
        var type: String = "",
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "sys_role_user",
                joinColumns = [JoinColumn(name="user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
        var roles: MutableList<Role> = mutableListOf(),
        @Transient
        var permissions: MutableList<Permission> = mutableListOf()
): SystemBase()
