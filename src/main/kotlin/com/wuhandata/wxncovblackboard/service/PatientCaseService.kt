package com.wuhandata.wxncovblackboard.service

import com.wuhandata.wxncovblackboard.common.vo.PatientCaseVO
import com.wuhandata.wxncovblackboard.domain.CaseDetail
import com.wuhandata.wxncovblackboard.domain.PatientCase
import com.wuhandata.wxncovblackboard.domain.PatientInfo
import com.wuhandata.wxncovblackboard.repo.CaseDetailRepository
import com.wuhandata.wxncovblackboard.repo.PatientCaseRepository
import com.wuhandata.wxncovblackboard.repo.PatientInfoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class PatientCaseService {
    protected val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var infoRepository: PatientInfoRepository

    @Autowired
    lateinit var detailRepository: CaseDetailRepository

    @Autowired
    lateinit var caseRepository: PatientCaseRepository


    fun save(openId: String, info: PatientInfo, detail: CaseDetail): Boolean {
        try {
            val patientId = infoRepository.saveAndFlush(info).id
            val detailId = detailRepository.saveAndFlush(detail).id
            if(!StringUtils.isEmpty(patientId) && !StringUtils.isEmpty(detailId)) {
                val case = PatientCase()
                case.openId = openId
                case.detailId = detailId
                case.patientId = patientId
                case.diagnosis = getDiagnosis()
                caseRepository.save(case)
            } else {
                if(StringUtils.isEmpty(patientId)) {
                    log.warn("用户基础信息保存失败")
                } else {
                    log.warn("用户医学信息保存失败")
                }
                return false
            }
        } catch (ex: Exception) {
            log.error(ex.message)
        }
        return true
    }


    fun getPatientCases(openId: String): List<PatientCaseVO> {
        val caseList = ArrayList<PatientCaseVO>()
        val cases = caseRepository.getPatientCasesByOpenId(openId);
        for(case in cases) {
            val patientInfo = infoRepository.findById(case.patientId)
            val caseDetail = detailRepository.findById(case.detailId)
            val vo = PatientCaseVO()
            if(patientInfo.isPresent) {
                vo.info = patientInfo.get()
            } else {
                vo.info = PatientInfo()
            }
            if(caseDetail.isPresent) {
                vo.detail = caseDetail.get()
            } else {
                vo.detail = CaseDetail()
            }
            caseList.add(vo)
        }
        return caseList
    }

    fun getDiagnosis(): String {
        return ""
    }

}
