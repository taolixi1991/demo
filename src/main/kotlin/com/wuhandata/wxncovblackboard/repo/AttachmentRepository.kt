package com.wuhandata.wxncovblackboard.repo

import com.wuhandata.wxncovblackboard.domain.Attachment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface AttachmentRepository: JpaRepository<Attachment, String>, JpaSpecificationExecutor<Attachment> {
//
//    @Query("SELECT * FROM `t_attachment` att INNER JOIN t_feedback_attachment fa on att.id = fa.attachment_id WHERE fa.feedback_id = ?1", nativeQuery = true)
//    fun findAllByFeedbackId(feedbackId: String): List<Attachment>

    fun findAllBySource(source: String): List<Attachment>

    fun findAllBySource(source: String, pageable: Pageable): Page<Attachment>
}
