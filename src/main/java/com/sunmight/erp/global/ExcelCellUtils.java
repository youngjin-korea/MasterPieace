package com.sunmight.erp.global;

import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelCellUtils {

    public static String getString(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                // 숫자를 문자열로 안전 변환
                double d = cell.getNumericCellValue();
                if (d == (long) d) {
                    return String.valueOf((long) d);
                } else {
                    return String.valueOf(d);
                }

            case FORMULA:
                return cell.getRichStringCellValue().getString().trim();

            case BLANK:
            default:
                return null;
        }
    }

    public static Integer getInteger(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();

            case STRING:
                String v = cell.getStringCellValue().trim();
                return Integer.parseInt(v);

            case FORMULA:
                return (int) cell.getNumericCellValue();

            default:
                return null;
        }
    }

    public static BigDecimal getBigDecimal(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                // 숫자를 String으로 안전 변환
                double d = cell.getNumericCellValue();
                String value = BigDecimal.valueOf(d).toPlainString();
                return new BigDecimal(value);

            case STRING:
                String str = cell.getStringCellValue().trim();
                if (str.isEmpty()) {
                    return null;
                }
                return new BigDecimal(str);

            case FORMULA:
                double fv = cell.getNumericCellValue();
                String fValue = BigDecimal.valueOf(fv).toPlainString();
                return new BigDecimal(fValue);

            default:
                return null;
        }
    }

}

