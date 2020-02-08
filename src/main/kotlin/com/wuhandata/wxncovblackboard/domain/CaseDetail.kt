package com.wuhandata.wxncovblackboard.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "t_detail")
class CaseDetail (
        var firstDate: LocalDateTime = LocalDateTime.now(),
        //发热
        var fever: Boolean = false,
        //发热, 0: 高于37,5；1: 高于38；0: 高于38,5；0: 高于39；
        var detail: Int = 0,
        //胸闷
        var chestDistress: Boolean = false,
        //气喘
        var asthma: Boolean = false,
        //食欲差
        var poorAppetite: Boolean = false,
        //腹泻
        var diarrhea: Boolean = false,
        //乏力
        var weakness: Boolean = false,
        //头晕
        var dizziness: Boolean = false,
        //流涕
        var runnyNose: Boolean = false,
        //精神状态差
        var poorMental: Boolean = false,
        //精神状态差
        var badSleep: Boolean = false,
        //头疼
        var headache: Boolean = false,
        //肌肉酸痛
        var muscleSoreness: Boolean = false,
        //高血压
        var hypertension: Boolean = false,
        //糖尿病
        var diabetesMellitus: Boolean = false,
        //高血脂
        var hyperlipemia: Boolean = false,
        //慢性支气管炎
        var chronicBronchitis: Boolean = false,
        //肺气肿
        var emphysema: Boolean = false,
        //心脏病
        var heartDisease: Boolean = false,
        //肿瘤
        var cancer: Boolean = false,
        //中风后遗症
        var strokeSequela: Boolean = false,
        //肿瘤
        var alzheimer: Boolean = false,

        @OneToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "t_detail_attachments",
                joinColumns = [JoinColumn(name="detail_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "attachment_id", referencedColumnName = "id")])
        var attachments: MutableList<Attachment> = mutableListOf()
): BaseDomain()
