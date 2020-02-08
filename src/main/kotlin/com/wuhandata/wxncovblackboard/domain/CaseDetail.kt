package com.wuhandata.wxncovblackboard.domain

import com.wuhandata.wxncovblackboard.common.constant.BodyStatus
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "t_detail")
class CaseDetail (
        var mental: BodyStatus = BodyStatus.MEDIUM,
        //肌肉酸痛
        var muscleSoreness: Boolean = false,
        //腹泻
        var diarrhea: Boolean = false,
        //乏力
        var weakness: Boolean = false,
        //睡眠
        var badSleep: BodyStatus = BodyStatus.MEDIUM,
        //食欲差
        var poorAppetite: BodyStatus = BodyStatus.MEDIUM,
        //如果为女性是否怀孕
        var pregnant: Boolean = false,
        //有无基础疾病
        var hasDisease: Boolean= false,
        var diseaseName: String = "",
        //特殊治疗
        var needTreatment: Boolean= false,
        var treatmentName: String = "",

        //发病前14天内有武汉市及周边地区，或其他有病例报告社区旅行史或居住史
        var visited: Boolean = false,
        //发病前14天内与新冠状病毒确诊者有接触史
        var directContact: Boolean = false,
        //发病前14天内曾接触过来自武汉市及周边地区，或来自其他有病例报告社区的疑似症状的患者
        var suspectedContact: Boolean = false,
        //聚集性发病
        var groupEffected: Boolean = false,

        //发热: 0: 小于37.3；1: 高于37.3；2: 高于39
        var fever: Int = 0,
        //咳嗽: 0: 10分钟以上咳一阵；1：10分钟内咳一阵；2：1-2分钟咳一阵
        var cough: Int = 0,
        //胸痛
        var pectoralgia: Int = 0,
        //呼吸困难
        var dyspnea: Int = 0,
        //呼吸次数增加，每分钟次数 0：<20; 1: > 24; 2: >30
        var breath: Int = 0,
        //嘴唇发紫, 0: 无， 1： 轻微； 2： 明显
        var lipsBlued: Int = 0,
        //发病早期白细胞正常或减少，或淋巴细胞计数减少
        var cellDrop: Boolean = false,
        //胸部X光片或CT显示肺部炎症性改变
        var ctDetected: Boolean = false,
        //呼吸道标本或血液标本RT-PCR检测新冠状病毒核酸阳性
        var positiveDetected: Boolean = false,
        //呼吸道标本或血液标本病毒基因测序与已知新冠状病毒同源
        var geneDetected: Boolean = false,

        //症状描述
        var description: String = "",
        //治疗情况
        var details: String = "",
        //最近就诊机构
        var closeHospital: String = "",

        @OneToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "t_detail_attachment",
                joinColumns = [JoinColumn(name="detail_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "attachment_id", referencedColumnName = "id")])
        var attachments: MutableList<Attachment> = mutableListOf()



): BaseDomain()
