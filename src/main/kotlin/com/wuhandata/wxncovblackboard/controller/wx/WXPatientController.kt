package com.wuhandata.wxncovblackboard.controller.wx

import com.wuhandata.wxncovblackboard.common.vo.Response
import com.wuhandata.wxncovblackboard.domain.CaseDetail
import com.wuhandata.wxncovblackboard.domain.PatientInfo
import com.wuhandata.wxncovblackboard.service.PatientCaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wxmp/patient")
class WXPatientController {

    @Autowired
    lateinit var patientCaseService: PatientCaseService

    @Autowired
    lateinit var attachmentService: PatientCaseService

    @PostMapping("/saveCase")
    @ResponseBody
    fun savePatient(@RequestBody info: PatientInfo, @RequestBody detail: CaseDetail,
                    @RequestBody openId: String, @RequestBody files: List<String>): Response{
        val response = Response()

        return response
    }

    @GetMapping("/getAll")
    fun getPatients(@RequestParam openId: String): Response{
        val response = Response()
        //List<PatientCase>
        return response
    }

    @GetMapping("/getCase")
    fun getPatientCase(@RequestParam patientId: String, @RequestParam detailId: String): Response{
        val response = Response()
        //PatientCaseVO
        return response
    }

    @GetMapping("/getFeedback")
    fun getFeedback(@RequestParam caseId: String): Response{
        val response = Response()
        //PatientCaseVO
        return response
    }
}
