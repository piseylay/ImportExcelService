package com.ig.importexel.service

import org.springframework.web.multipart.MultipartFile

interface ExcelService {
    fun save(file: MultipartFile?)
}