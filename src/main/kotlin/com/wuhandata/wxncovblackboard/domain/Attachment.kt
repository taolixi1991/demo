package com.wuhandata.wxncovblackboard.domain

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "t_attachment")
class Attachment (
        var name: String = "",
        //附件来源
        var source: String = "",
        var detailId: String = "",
        //病例文件类型
        var type: String = "",
        var path: String = ""
): BaseDomain()
