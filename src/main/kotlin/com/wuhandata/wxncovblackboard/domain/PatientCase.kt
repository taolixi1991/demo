package com.wuhandata.wxncovblackboard.domain

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "t_customer_patient")
class PatientCase {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(length = 32)
    var id: String = ""
    var openId: String = ""
    var patientId: String = ""
    var detailId: String = ""
}
