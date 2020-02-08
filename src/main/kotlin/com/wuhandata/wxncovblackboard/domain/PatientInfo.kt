package com.wuhandata.wxncovblackboard.domain

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "t_patient")
class PatientInfo (
        var name: String = "",
        var gender: String = "",
        var idNumber: String = "",
        var age: String = "",
        var height: String = "",
        var weight: String = "",
        //患者状态：居家隔离：0， 隔离点隔离：1，住院：2
        var status: Int = 0,
        var treatmentAddress: String = "",
        //患者状态：重度确诊：1，轻度确证：2，重度疑似：3，轻度疑似：4，发热肺炎患者且明确排除：5，有接触确诊患者：6，痊愈：0
        var caseStatus: Int = 0,
        var address: String= "",
        var contactName: String = "",
        var phoneNumber: String = ""
): BaseDomain()
