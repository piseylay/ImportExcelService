package com.ig.importexel.controller

import com.ig.importexel.helper.ExcelHelper
import com.ig.importexel.message.ResponseMessage
import com.ig.importexel.service.ExcelService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.task.TaskExecutor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("excel")
class ExcelController {
    @Autowired
    lateinit var fileService: ExcelService
    @Autowired
    lateinit var taskExecutor: TaskExecutor

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<ResponseMessage?>? {
        var message: String
        if (ExcelHelper.hasExcelFormat(file)) {
            return try {
                taskExecutor.execute {
                    fileService.save(file)
                }
                message = "Uploaded the file successfully: " + file.originalFilename
                ResponseEntity.status(HttpStatus.OK).body<ResponseMessage?>(ResponseMessage(message))
            } catch (e: Exception) {
                message = "Could not upload the file: " + file.originalFilename + "!"
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body<ResponseMessage?>(ResponseMessage(message))
            }
        }
        message = "Please upload an excel file!"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body<ResponseMessage?>(ResponseMessage(message))
    }
}