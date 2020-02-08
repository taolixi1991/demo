package com.wuhandata.wxncovblackboard.domain.system

import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "sys_role")
class Role(
        var name: String = "",
        var mask: String = "",
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "sys_permission_role",
                joinColumns = [JoinColumn(name="role_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "permission_id", referencedColumnName = "id")])
        var permissions: MutableList<Permission> = mutableListOf(),
        @Transient
        var hasRole: Boolean = false
): SystemBase()
