package com.wuhandata.wxncovblackboard.domain.system

import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "sys_option")
@Entity
open class SpinnerOption (
        var type: String = "",
        var name: String = "",
        var parentId: String = ""
): SystemBase()
