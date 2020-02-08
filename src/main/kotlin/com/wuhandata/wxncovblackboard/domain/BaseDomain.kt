package com.wuhandata.wxncovblackboard.domain

import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseDomain: Serializable {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(length = 32)
    var id: String = ""

    @CreatedDate
    open var dateCreated: LocalDateTime = LocalDateTime.now()

    @CreatedBy
    open var createdBy: String = ""

    @LastModifiedDate
    open var lastUpdated: LocalDateTime = LocalDateTime.now()

    @LastModifiedBy
    open var lastUpdatedBy: String = ""

    @Version
    open var version: Long = 0L
}
