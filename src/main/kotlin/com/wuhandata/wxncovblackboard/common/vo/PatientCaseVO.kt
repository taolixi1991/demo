package com.wuhandata.wxncovblackboard.common.vo

import com.wuhandata.wxncovblackboard.domain.CaseDetail
import com.wuhandata.wxncovblackboard.domain.PatientInfo

class PatientCaseVO (
    var info: PatientInfo = PatientInfo(),
    var detail: CaseDetail = CaseDetail()
)
