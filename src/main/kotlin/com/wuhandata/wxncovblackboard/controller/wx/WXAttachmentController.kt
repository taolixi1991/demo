package com.wuhandata.wxncovblackboard.controller.wx

import com.wuhandata.wxncovblackboard.common.vo.Response
import com.wuhandata.wxncovblackboard.domain.Attachment
import com.wuhandata.wxncovblackboard.service.AttachmentService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/wxmp/attachment")
class WXAttachmentController {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var uploadService: AttachmentService


    @PostMapping("/upload")
    @ResponseBody
    fun uploadAttachment(request: HttpServletRequest,
                         @ModelAttribute("attachment")attachment: Attachment,
                         @RequestParam("file")file: MultipartFile): Response {
        val response = Response()
        val fileName = attachment.name
        if(StringUtils.isEmpty(fileName)) {
            response.code = 1
            response.msg = "文件用户名无效"
            return response
        } else {
            val filePath = uploadService.uploadMultipartFile(attachment.source, fileName, file)
            if(StringUtils.isEmpty(filePath)) {
                response.code = 1
                response.msg = "文件保存失败"
                return response
            } else {
                val newAttach = uploadService.saveAttachment(filePath, attachment)
                response.code = 0
                response.data = newAttach
            }
        }
        return response
    }
}
