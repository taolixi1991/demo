package com.wuhandata.wxncovblackboard.domain.system

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "sys_permission")
class Permission(
        var name: String = "",
        var mask: String = "",
        var url: String = "",
        var css: String = "",
        var method: String = "",
        var type: String = "",
        var parentId: String = "",
        var seq: Int = 0,
        @Transient
        var children: MutableList<Permission> = mutableListOf(),
        @Transient
        var hasPermission: Boolean = false
): SystemBase()
