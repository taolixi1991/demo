package com.wuhandata.wxncovblackboard.service

import com.wuhandata.wxncovblackboard.common.utils.Helper
import com.wuhandata.wxncovblackboard.common.vo.DataTable
import com.wuhandata.wxncovblackboard.common.vo.DataTableParams
import com.wuhandata.wxncovblackboard.domain.Attachment
import com.wuhandata.wxncovblackboard.repo.AttachmentRepository
import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.net.InetAddress
import java.util.UUID

@Service
class AttachmentService {
    protected val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${file.storePath:/user/}")
    private val filePath: String = "/user/"

    @Value("\${smartSite.website:/user/}")
    private val address: String = ""

    @Autowired
    lateinit var attachmentRepo: AttachmentRepository

    fun saveAttachment(path: String, attachment: Attachment): Attachment? {
        var attachmentId = ""
        var dateCreated = ""
        var result: Attachment? = null
        try {
            attachment.path = path
            result = attachmentRepo.saveAndFlush(attachment)
            attachmentId = result.id
            dateCreated = result.dateCreated.toString()
        } catch (e: Exception) {
            log.error(e.message)
        }
        log.info("上传附件记录保存成功，附件Id: $attachmentId, 保存时间为: $dateCreated")
        return result
    }

    fun delAttachment(id: String) {
        try {
            attachmentRepo.deleteById(id)
        } catch (e: Exception) {
            log.error(e.message)
        }
    }

    fun getAttachment(id: String): Attachment? {
        var attachment: Attachment? = null
        try {
            val optional = attachmentRepo.findById(id)
            if(optional.isPresent) {
                attachment = optional.get()
            }
        } catch (e: Exception) {
            log.error(e.message)
        }
        return attachment
    }

    fun getAttachmentsByDetailId(DetailId: String): List<Attachment> {
        var list = emptyList<Attachment>()
        try {
            list = attachmentRepo.findAllByFeedbackId(DetailId)
        } catch (e: Exception) {
            log.error(e.message)
        }
        return list
    }

    fun getMainPageSliders(): List<String> {
        var list = ArrayList<String>()
        val addr = if(isLocal()) {
            val ip = InetAddress.getLocalHost().hostAddress
            "http://$ip"
        } else {
            address
        }
        try {
            val attachmentList = attachmentRepo.findAllBySource("slider")
            for(attachment in attachmentList) {
                list.add(addr + attachment.path)
            }
        } catch (e: Exception) {
            log.error(e.message)
        }
        return list
    }

    fun getAttachmentsDataTable(params: DataTableParams): DataTable<Attachment> {
        val pageable = Helper.makePage(params)
        val list = attachmentRepo.findAllBySource("slider", pageable)
        val dt = DataTable<Attachment>()
        dt.data = list.content
        dt.recordsFiltered = list.totalElements
        dt.recordsTotal = list.totalElements
        return dt
    }


    /**
     *  上传文件，文件地址解析为，该APP文件存放文件夹/父项ID/文件对应的随机ID,
     */
    fun uploadMultipartFile(directory: String, fileName: String, file: MultipartFile): String {
        var uploadDestination: String
        try {
            if(file.originalFilename == null) {
                return ""
            }
            val targetFileName = generateTmpFile(file.originalFilename!!)
            // 将上传文件复制到临时文件
            uploadDestination = filePath + directory +  File.separator + targetFileName
            FileUtils.copyInputStreamToFile(file.inputStream, File(uploadDestination))
        } catch (e: Exception) {
            log.warn("文件：$fileName 上传失败")
            log.error(e.message)
            uploadDestination = ""
        }
        log.info("文件：$fileName 上传成功至文件地址：$uploadDestination")
        return uploadDestination
    }

    private fun generateTmpFile(fileName: String):String {
        val suffix = fileName!!.substring(fileName.lastIndexOf("."))
        val id = UUID.randomUUID().toString()
        File.createTempFile(id, suffix)
        return id +suffix
    }

    private fun isLocal(): Boolean {
        val os = System.getProperty("os.name")
        if(os.toLowerCase().startsWith("win")) {
            return true
        }
        return false
    }
}
