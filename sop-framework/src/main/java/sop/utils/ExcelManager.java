package sop.utils;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:28
 * @Package: sop.utils
 */

public class ExcelManager {

    private static final String DEFAULT_SHEET_NAME = "sheet";

    /**
     * excel注入数据
     */
    public static Workbook importToExcel(String templateFileName, Map<String, Object> beans) {
        XLSTransformer transformer = new XLSTransformer();
        InputStream in = null;
        Workbook workbook = null;
        try {
            in = ExcelManager.class.getClassLoader().getResourceAsStream(templateFileName);
            if (in != null) {
                workbook = transformer.transformXLS(in, beans);
                in.close();
            }
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * excel注入数据
     */
    public static void importToExcel(Workbook workbook, Map<String, Object> beans) {
        XLSTransformer transformer = new XLSTransformer();
        try {
            if (workbook != null) {
                transformer.transformWorkbook(workbook, beans);
            }
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        }
    }

    /**
     * excel导出到本地目录
     */
    public static void outputExcelFileToLocal(Workbook workbook, String encodeQcNo, String itCatNoSuffix, String templateFileName) {
        Map<String, String> ftpPropertiesMap = Common.getProperties("/temp.properties");
        String baselocation = ftpPropertiesMap.get("baselocation");
        String saveBasePath = baselocation + "/" + encodeQcNo + "/" + itCatNoSuffix;
        String savePath = saveBasePath + "/" + templateFileName;
        Common.mkDirs(saveBasePath);

        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(savePath));
            workbook.write(bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * excel合并
     */
    public static Workbook concatExcel(LinkedHashMap<String, Workbook> workbooks) {
        Workbook returnWorkbook = null;
        if (workbooks != null && workbooks.size() > 0) {
            XLSTransformer transformer = new XLSTransformer();
            try {
                returnWorkbook = transformer.transformXLS(ExcelManager.class.getClassLoader().getResourceAsStream("itemtemplates/concat.xls"), null);
                for (Map.Entry<String, Workbook> workbookEntry : workbooks.entrySet()) {
                    System.out.print(workbookEntry.getKey() + ":" + workbookEntry.getValue() + "\t");

                }
            } catch (ParsePropertyException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }

        }
        return returnWorkbook;
    }

    /**
     * 渲染Excel模块并在浏览器上导出Excel
     */
    public static void exportExcel(HttpServletResponse response, Workbook workbook, String outPutFileName) {

        // 设置响应
        response.setHeader("Content-Disposition", "attachment;filename=" + outPutFileName);
        response.setContentType("application/vnd.ms-excel");

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            // 将内容写入输出流并把缓存的内容全部发出去
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出无动态表头的Excel文件
     * <p>
     * 参考重载的有动态表头注释
     * </p>
     *
     * @param destOutputStream
     * @param templateInputStream
     * @param data
     * @param dataKey
     * @param maxRowPerSheet
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void generateExcelByTemplate(OutputStream destOutputStream,
                                               InputStream templateInputStream,
                                               List data, String dataKey,
                                               int maxRowPerSheet) throws Exception {
        generateExcelByTemplate(destOutputStream,
                templateInputStream,
                null, null,
                data, dataKey,
                maxRowPerSheet);
    }

    /**
     * 通过Excel模版生成Excel文件
     * <p>
     * 创建Excel模版，变量类似JSP tag风格。
     * 例如：
     * <ul>
     * <li>无动态表头
     * <pre>
     * 序号   名称  规格  创建时间    价格
     * &lt;jx:forEach items="${vms}" var="vm"&gt;
     * ${vm.id} ${vm.name} ${vm.scale} ${vm.created} ${vm.price}
     * &lt;/jx:forEach&gt;
     * </pre>
     * </li>
     * <li>有动态表头
     * <pre>
     * 项目/数量/时间    &lt;jx:forEach items="${dates}" var="date"&gt;    ${date} &lt;/jx:forEach&gt;
     * &lt;jx:forEach items="${itemsx}" var="item"&gt;
     * ${item.name}    &lt;jx:forEach items="${item.counts}" var="count"&gt; ${count}    &lt;/jx:forEach&gt;
     * &lt;/jx:forEach&gt;
     * </pre>
     * </li>
     * </ul>
     * 调用该方法则生成对应的Excel文件。
     * </p>
     * <p>
     * 注意：dataKey不能是items, items是保留字，如果用items则会提示：Collection is null并抛出NullPointerException
     * </p>
     *
     * @param destOutputStream    Excel输出流
     * @param templateInputStream Excel模版输入流
     * @param header              动态表头
     * @param headerKey           表头的变量
     * @param data                数据项
     * @param dataKey             数据项变量
     * @param maxRowPerSheet      每个sheet最多行数
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void generateExcelByTemplate(OutputStream destOutputStream,
                                               InputStream templateInputStream,
                                               List header, String headerKey,
                                               List data, String dataKey,
                                               int maxRowPerSheet) throws Exception {

        List<List> splitData = null;
        @SuppressWarnings("unchecked")
        Map<String, List> beanMap = new HashMap<String, List>();
        List<String> sheetNames = new ArrayList<String>();
        if (data.size() > maxRowPerSheet) {
            splitData = splitList(data, maxRowPerSheet);
            sheetNames = new ArrayList<String>(splitData.size());
            for (int i = 0; i < splitData.size(); ++i) {
                sheetNames.add(DEFAULT_SHEET_NAME + i);
            }
        } else {
            splitData = new ArrayList<List>();
            sheetNames.add(DEFAULT_SHEET_NAME + 0);
            splitData.add(data);
        }
        if (null != header) {
            beanMap.put(headerKey, header);
        }
        XLSTransformer transformer = new XLSTransformer();
        Workbook workbook = transformer.transformMultipleSheetsList(
                templateInputStream, splitData, sheetNames, dataKey, beanMap, 0);
        workbook.write(destOutputStream);
    }


    /**
     * 导出无动态表头的Excel文件，目标文件和模版文件均为文件路径
     * <p>
     * 参考重载的有动态表头注释
     * </p>
     *
     * @param destFilePath
     * @param templateFilePath
     * @param data
     * @param dataKey
     * @param maxRowPerSheet
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void generateExcelByTemplate(String destFilePath,
                                               String templateFilePath,
                                               List data, String dataKey, int maxRowPerSheet) throws Exception {
        generateExcelByTemplate(destFilePath, templateFilePath, null, null, data, dataKey, maxRowPerSheet);
    }

    /**
     * 导出有动态表头的Excel文件，目标文件和模版文件均为文件路径
     * <p>
     * 参考重载的有动态表头注释
     * </p>
     *
     * @param destFilePath
     * @param templateFilePath
     * @param header
     * @param headerKey
     * @param data
     * @param dataKey
     * @param maxRowPerSheet
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void generateExcelByTemplate(String destFilePath,
                                               String templateFilePath,
                                               List header, String headerKey,
                                               List data, String dataKey, int maxRowPerSheet) throws Exception {
        generateExcelByTemplate(new FileOutputStream(destFilePath),
                new FileInputStream(templateFilePath),
                header, headerKey,
                data, dataKey, maxRowPerSheet);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static List<List> splitList(List data, int maxRowPerSheet) {
        List<List> splitData = new ArrayList<List>();
        List sdata = null;
        for (int i = 0; i < data.size(); ++i) {
            if (0 == i % maxRowPerSheet) {
                if (null != sdata) {
                    splitData.add(sdata);
                }
                sdata = new ArrayList(maxRowPerSheet);
            }
            sdata.add(data.get(i));
        }
        if (0 != maxRowPerSheet % data.size()) {
            splitData.add(sdata);
        }

        return splitData;
    }

    /*
     * 对SHeet进行复制，将源Excle保存到目标Excle中
     */
    public static HSSFWorkbook copyRows(HSSFWorkbook wb, HSSFWorkbook pTargetWb) {
        int pStartRow = 0;                       //开始行
        int pEndRow = 0;                          //结束行
        int pPosition = 0;                       //位置
        String pSourceSheetName = "";
        String pTargetSheetName = "";
        HSSFRow sourceRow = null;
        HSSFRow targetRow = null;
        HSSFCell sourceCell = null;
        HSSFCell targetCell = null;
        HSSFSheet sourceSheet = null;
        HSSFSheet targetSheet = null;
        Region region = null;
        int cType;
        int i;
        short j;
        int targetRowFrom;
        int targetRowTo;
        if ((pStartRow == -1) || (pEndRow == -1)) {
            return null;
        }
        pSourceSheetName = wb.getSheetName(0);
        pTargetSheetName = pSourceSheetName;
        int pTargetSheetNumber = pTargetWb.getNumberOfSheets() + 1;
        if (pTargetWb.getSheet(pTargetSheetName) != null) {
            pTargetSheetName = pTargetSheetName + pTargetSheetNumber;
        }
        pTargetWb.createSheet(pTargetSheetName);
        sourceSheet = wb.getSheet(pSourceSheetName);
        targetSheet = pTargetWb.getSheet(pTargetSheetName);
        pEndRow = sourceSheet.getPhysicalNumberOfRows();
        // 拷贝合并的单元格
        for (i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            region = sourceSheet.getMergedRegionAt(i);
            if ((region.getRowFrom() >= pStartRow)
                    && (region.getRowTo() <= pEndRow)) {
                targetRowFrom = region.getRowFrom() - pStartRow + pPosition;
                targetRowTo = region.getRowTo() - pStartRow + pPosition;
                region.setRowFrom(targetRowFrom);
                region.setRowTo(targetRowTo);
                targetSheet.addMergedRegion(region);
            }
        }
        // 设置列宽
        for (i = pStartRow; i <= pEndRow; i++) {
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow != null) {
                for (j = sourceRow.getLastCellNum(); j > sourceRow
                        .getFirstCellNum(); j--) {
                    targetSheet
                            .setColumnWidth(j, sourceSheet.getColumnWidth(j));
                    targetSheet.setColumnHidden(j, false);
                }
                break;
            }
        }
        // 拷贝行并填充数据
        for (; i <= pEndRow; i++) {
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow == null) {
                continue;
            }
            targetRow = targetSheet.createRow(i - pStartRow + pPosition);
            targetRow.setHeight(sourceRow.getHeight());
            HSSFCellStyle style = pTargetWb.createCellStyle();
            for (j = sourceRow.getFirstCellNum(); j < sourceRow
                    .getPhysicalNumberOfCells(); j++) {
                sourceCell = sourceRow.getCell(j);
                if (sourceCell == null) {
                    continue;
                }
                targetCell = targetRow.createCell(j);
                //targetCell.setEncoding(sourceCell);
                //targetCell.setCellStyle(sourceCell.getCellStyle());
                /*这里的表样式需要另外处理，否则不同的Excel的样式不同，会引起错误。
                 * This Style does not belong to the supplied Workbook. Are you trying to assign a style from one workbook to the cell of a differnt workbook
                 */
                String sCellstyle = getCellStyle(sourceCell, wb);
                System.out.println(sCellstyle);
                targetCell.setCellStyle(createCellStyle(style, pTargetWb, sCellstyle));
                cType = sourceCell.getCellType();
                targetCell.setCellType(cType);
                switch (cType) {
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        targetCell.setCellValue(sourceCell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        targetCell
                                .setCellErrorValue(sourceCell.getErrorCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        // parseFormula这个函数的用途在后面说明
                        targetCell.setCellFormula(parseFormula(sourceCell
                                .getCellFormula()));
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        targetCell.setCellValue(sourceCell.getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        targetCell
                                .setCellValue(sourceCell.getRichStringCellValue());
                        break;
                }
            }
        }
        return pTargetWb;
    }

    /*
     * 公式处理
     */
    private static String parseFormula(String pPOIFormula) {
        final String cstReplaceString = "ATTR(semiVolatile)"; //$NON-NLS-1$
        StringBuffer result = null;
        int index;
        result = new StringBuffer();
        index = pPOIFormula.indexOf(cstReplaceString);
        if (index >= 0) {
            result.append(pPOIFormula.substring(0, index));
            result.append(pPOIFormula.substring(index
                    + cstReplaceString.length()));
        } else {
            result.append(pPOIFormula);
        }
        return result.toString();
    }


    /*
     * 根据传入的单元格格式,设置单元格的样式
     */
    public static HSSFCellStyle createCellStyle(HSSFCellStyle prestyle, HSSFWorkbook workbook, String cellStyle) {
        HSSFCellStyle style = workbook.createCellStyle();
        String[] css = cellStyle.split(";");
        style.setAlignment(Short.valueOf((css[0])));
        style.setBorderBottom(Short.valueOf((css[1])));
        style.setBorderLeft(Short.valueOf((css[2])));
        style.setBorderRight(Short.valueOf((css[3])));
        style.setBorderTop(Short.valueOf((css[4])));
        style.setBottomBorderColor(Short.valueOf((css[5])));
        style.setDataFormat(Short.valueOf((css[6])));
        style.setFillBackgroundColor(Short.valueOf((css[7])));
        style.setFillForegroundColor(Short.valueOf((css[8])));
        style.setFillPattern(Short.valueOf((css[9])));
        style.setLeftBorderColor(Short.valueOf((css[10])));
        style.setRightBorderColor(Short.valueOf((css[11])));
        style.setRotation(Short.valueOf((css[12])));
        style.setTopBorderColor(Short.valueOf((css[13])));
        style.setVerticalAlignment(Short.valueOf((css[14])));
        style.setWrapText(Boolean.valueOf(css[15]));
        // 对字体的处理
        HSSFFont font = workbook.createFont();
        font.setBoldweight(Short.valueOf((css[16])));
        font.setColor(Short.valueOf((css[17])));
        font.setFontHeight(Short.valueOf((css[18])));
        font.setFontHeightInPoints(Short.valueOf((css[19])));
        font.setFontName(css[20]);
        style.setFont(font);
        return style;
    }

    /*
     * 获取单元格的格式信息,并返回.
     */
    public static String getCellStyle(HSSFCell cell, HSSFWorkbook wb) {
        String temp = "";
        HSSFCellStyle style = cell.getCellStyle();
        temp = temp + style.getAlignment() + ";";
        temp = temp + style.getBorderBottom() + ";";
        temp = temp + style.getBorderLeft() + ";";
        temp = temp + style.getBorderRight() + ";";
        temp = temp + style.getBorderTop() + ";";
        temp = temp + style.getBottomBorderColor() + ";";
        temp = temp + style.getDataFormat() + ";";
        temp = temp + style.getFillBackgroundColor() + ";";
        temp = temp + style.getFillForegroundColor() + ";";
        temp = temp + style.getFillPattern() + ";";
        temp = temp + style.getLeftBorderColor() + ";";
        temp = temp + style.getRightBorderColor() + ";";
        temp = temp + style.getRotation() + ";";
        temp = temp + style.getTopBorderColor() + ";";
        temp = temp + style.getVerticalAlignment() + ";";
        temp = temp + style.getWrapText() + ";";
        // 对字体的处理
        temp = temp + style.getFont(wb).getBoldweight() + ";";
        temp = temp + style.getFont(wb).getColor() + ";";
        temp = temp + style.getFont(wb).getFontHeight() + ";";
        temp = temp + style.getFont(wb).getFontHeightInPoints() + ";";
        temp = temp + style.getFont(wb).getFontName();
        return temp;
    }
}
