package com.ig.importexel.service.implement

import com.ig.importexel.controller.ExcelController
import com.ig.importexel.helper.ExcelHelper
import com.ig.importexel.model.Employee
import com.ig.importexel.repository.EmployeeRepository
import com.ig.importexel.service.ExcelService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
class ExcelServiceImpl : ExcelService{

    @Autowired
    lateinit var employeeRepository: EmployeeRepository
    var logger: Logger = LoggerFactory.getLogger(ExcelController::class.java)

    override fun save(file: MultipartFile?) {
        try {
            val start = System.currentTimeMillis()
            val employee: List<Employee> = ExcelHelper.excelToEmployee(file?.inputStream)
            employeeRepository.saveAll(employee)
            val end = System.currentTimeMillis()
            logger.info("Total Time : {}", (end-start))
        } catch (e: IOException) {
            throw RuntimeException("fail to store excel data: " + e.message)
        }
    }
}