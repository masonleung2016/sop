package sop.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import dwz.common.util.StringUtils;
import sop.util.lang.StringUtil;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:32
 * @Package: sop.utils
 */

public class TemplateCodegen {

    public static void main(String[] args) {
        FileInputStream is = null;
        try {
            is = new FileInputStream("D:/rrr/qc-forms20150706/Re_ BOM 标准化的资料---2016.7.6/c.xls");
            POIFSFileSystem fs = new POIFSFileSystem(is);
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = wb.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            for (int i = 0; i < rows + 1; i++) {
                readRow(sheet, i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void readRow(HSSFSheet sheet, int row) {
        int cols = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < cols; i++) {
            String s = readCell(sheet, i, row);
            System.out.println(s);

        }
    }

    public static String readCell(HSSFSheet sheet, int colIndex, int rowIndex) {
        StringBuilder sb = new StringBuilder();
        String val = PoiUtil.getCellValue(sheet, colIndex, rowIndex);
        System.out.println(val);
        if (!StringUtils.isBlank(val)) {
            if (val.contains("；")) {
                sb.append(optionTemplate(val.split("；")));
            }
            if (val.contains("、")) {
                sb.append(optionTemplate(val.split("、")));
            }
        }

        return sb.toString();
    }

    private static String optionTemplate(String[] options) {
        StringBuilder sb = new StringBuilder();
        sb.append("<select id=\"${idPrefix}${idx}2\" class=\"detailInputData textInput\" ${fieldDisable} value=\"${detailVo[field]}\" >\n");
        sb.append("<option value=\"\">--</option>\n");
        for (String s : options) {
            sb.append("<option ${detailVo[field] eq '" + s + "'?'selected':''}>" + s + "</option>\n");
        }

        sb.append("</select>\n");
        return sb.toString();
    }
}
