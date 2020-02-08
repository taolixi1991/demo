package com.wuhandata.wxncovblackboard.domain

import com.wuhandata.wxncovblackboard.common.constant.FamilyRelationship
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.TemporalType

@Entity
@Table(name = "t_patient")
class PatientInfo (
        var name: String = "",
        var gender: String = "",
        var idNumber: String = "",
        var phoneNumber: String = "",
        var address: String= "",
        var fullAddress: String= "",
        var relationship: FamilyRelationship = FamilyRelationship.SELF,
        var effected: Boolean = false,
        var diseaseName: String = "",
        @Temporal(TemporalType.DATE)
        var effectedDate: LocalDateTime = LocalDateTime.now()
): BaseDomain()
