package com.datn.ExcelHelper;

import com.datn.entity.Order;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {  "Tên Người nhận","Mã Đơn Hàng", "Tổng tiền", "Số lượng","Ngày tạo đơn","Địa chỉ giao hàng","Số điện thoại người nhận" };
    static String SHEET = "HoaDon";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }
    public static byte[] tutorialsToExcel(List<Order> ordersList) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }
            int rowIdx = 1;
            for (Order order : ordersList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(order.getUsernameKH());
                row.createCell(1).setCellValue(order.getCode());
                row.createCell(2).setCellValue(order.getTotalResult());
                row.createCell(3).setCellValue(order.getNumberProduct());
                row.createCell(4).setCellValue(order.getCreatedDate());
                row.createCell(5).setCellValue(order.getDeliveryAddress());
                row.createCell(6).setCellValue(order.getPhoneNumber());
            }
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
