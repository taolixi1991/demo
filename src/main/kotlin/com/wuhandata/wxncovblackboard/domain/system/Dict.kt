package com.wuhandata.wxncovblackboard.domain.system

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "sys_dict")
class Dict(
        var name: String = "",
        var value: String = "",
        var parentId: Long = 0L,
        var mask: String = "",
        var parentMask: String = "",
        @Transient
        var dicts: MutableList<Dict> = ArrayList(),
        @Transient
        var has: Boolean = false,
        @Transient
        var children: Boolean = false
): SystemBase()
