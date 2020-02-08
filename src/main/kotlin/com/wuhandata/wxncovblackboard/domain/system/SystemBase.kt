package com.wuhandata.wxncovblackboard.domain.system

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class SystemBase: Serializable {
    @Id
    @GeneratedValue
    var id: Long = 0L

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
