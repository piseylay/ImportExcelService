package com.ig.importexel.helper

import com.ig.importexel.model.Employee
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream

object ExcelHelper {
    private var TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    private var SHEET = "Sheet1"
    fun hasExcelFormat(file: MultipartFile): Boolean {
        return TYPE == file.contentType
    }

    fun excelToEmployee(`is`: InputStream?): List<Employee> {
        return try {
            val workbook: Workbook = XSSFWorkbook(`is`)
            val sheet = workbook.getSheet(SHEET)
            val rows: Iterator<Row> = sheet.iterator()
//            skipped first row header
            rows.next()
            val employee: MutableList<Employee> = ArrayList()
            while (rows.hasNext()) {
                val currentRow = rows.next()
                val cellsInRow: Iterator<Cell> = currentRow.iterator()
                val emp = Employee()
                var cellIdx = 0
                while (cellsInRow.hasNext()) {
                    val currentCell = cellsInRow.next()
                    when (cellIdx) {
                        0 -> emp.id = currentCell.numericCellValue.toLong()
                        1 -> emp.firstName = currentCell.stringCellValue
                        2 -> emp.lastName = currentCell.stringCellValue
                        3 -> emp.address = currentCell.stringCellValue
                        4 -> emp.phone = currentCell.stringCellValue
                    }
                    cellIdx++
                }
                    employee.add(emp)
            }
            workbook.close()
            employee
        } catch (e: IOException) {
            throw RuntimeException("fail to parse Excel file: " + e.message)
        }
    }
}
