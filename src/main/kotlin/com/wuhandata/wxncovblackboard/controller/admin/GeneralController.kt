package com.wuhandata.wxncovblackboard.controller.admin

import com.fasterxml.jackson.databind.ObjectMapper
import com.wuhandata.wxncovblackboard.common.vo.DataTableParams
import com.wuhandata.wxncovblackboard.common.vo.Response
import com.wuhandata.wxncovblackboard.domain.Attachment
import com.wuhandata.wxncovblackboard.service.AttachmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/general")
class GeneralController {

    @Autowired
    lateinit var attachmentService: AttachmentService

    val mapper = ObjectMapper()


    @GetMapping("/index")
    fun index(): String{
        return "general/index.html"
    }

    @GetMapping("/create")
    fun create(model: Model): String{
        model.addAttribute("slider", Attachment())
        return "general/save.html"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable("id")id: String, model: Model): String{
        model.addAttribute("attachment", attachmentService.getAttachment(id))
        return "general/edit.html"
    }

    @ResponseBody
    @PostMapping("/index")
    fun getList(params: DataTableParams): String {
        val list = attachmentService.getAttachmentsDataTable(params)
        list.draw = params.draw
        return mapper.writeValueAsString(list)
    }

    @PostMapping("/upload")
    @ResponseBody
    fun uploadSlider(request: HttpServletRequest,
                     @ModelAttribute("name")name: String,
                     @RequestParam("file")file: MultipartFile): Response {
        val  response = Response()
        val path = attachmentService.uploadMultipartFile("slider", name, file)
        var attachment = Attachment()
        attachment.name = name
        attachment.source = "slider"
        attachmentService.saveAttachment(path, attachment)
        if(StringUtils.isEmpty(path)) {
            response.code = 1
            response.msg = "fail"
        } else {
            response.code = 0
        }
        return response
    }

    @ResponseBody
    @PostMapping("/delete")
    fun delete(ids: String): Response{
        val  response = Response()
        if(!StringUtils.isEmpty(ids)) {

            attachmentService.delAttachment(ids)
            response.code = 0
            response.msg = "success"
        } else {
            response.code = 1
            response.msg = "fail"
        }
        return response
    }
}
