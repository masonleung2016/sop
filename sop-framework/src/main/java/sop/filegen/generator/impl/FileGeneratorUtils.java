package sop.filegen.generator.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrTokenizer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import sop.constant.DateFormatConstant;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;
import sop.util.DateTypeAdapter;
import sop.util.DateUtils;
import sop.util.Sys;
import sop.util.io.FileUtil;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:47
 * @Package: sop.filegen.generator.impl
 */


public final class FileGeneratorUtils {

    public static final String JSON_DATE_FORMAT = DateTypeAdapter.JSON_DATE_FORMAT;
    private static final Logger LOG = LoggerFactory.getLogger(FileGeneratorUtils.class);

    private FileGeneratorUtils() {

    }

    public static String getDocxTemplate(String templateName) {
        return FileUtil.mergeSubDirectory(new String[]{System.getProperty("rrr.config.path"), "/docxTemplates"}) + templateName + ".docx";
    }

    public static String getDocxTemplatePath() {
        return FileUtil.mergeSubDirectory(new String[]{System.getProperty("rrr.config.path"), "/docxTemplates"});
    }

    public static String getMailMergeTemplatePath() {
        return FileUtil.mergeSubDirectory(new String[]{System.getProperty("rrr.config.path"), "/mailMerge/docxTemplates"});
    }

    public static String getMailMergeSQLPath() {
        return FileUtil.mergeSubDirectory(new String[]{System.getProperty("rrr.config.path"), "/mailMerge/sqlTemplates"});
    }

    public static String toJson(Object request) {
        return JsonUtil.toJson(request);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return JsonUtil.fromJson(json, clazz);
    }

    public static void makeDirIfNotExists(String outputPath) {
        if (!StringUtils.isEmpty(outputPath)) {
            File dir = new File(outputPath);
            if (!dir.exists()) {
                boolean rt = dir.mkdirs();
                if (rt) {
                    LOG.debug("Make dir: {}", outputPath);
                }
            }
        }
    }

    public static String getReportOutputFullPath(String outputPath, String reportFileName, String fileExt) {
        FileGeneratorUtils.makeDirIfNotExists(outputPath);
        StringBuilder sb = new StringBuilder();
        sb.append(outputPath);
        if (!StringUtils.endsWith(outputPath, "/")) {
            sb.append("/");
        }
        sb.append(reportFileName);
        sb.append(fileExt);
        return sb.toString();
    }

    public static String genReportFileName(GenRequest request) {
        Assert.notNull(request, "GenRequest parameter can not be null.");
        Assert.notNull(request.getReportId(), "GenRequest reportId parameter can not be null.");

        Date requestDatetime = request.getRequestDatetime() != null ? request.getRequestDatetime() : Sys.getServerDate();
        String userId = null;
        if (StringUtils.isNotBlank(request.getRequestUserId())) {
            userId = StringUtils.trimToEmpty(request.getRequestUserId());
        } else {
            userId = "SYSTEM";
        }
        //return StringUtils.upperCase(request.getReportId() + "_" + userId + "_" + DateUtils.formatDateTime(DateFormatConstant.DATETIME_WITHOUT_SEPARATOR_LONG, requestDatetime));
        return StringUtils.upperCase(request.getReportId() + "_" + userId + "_" + DateUtils.formatDateTime(DateFormatConstant.DATETIME_WITHOUT_SEPARATOR_LONG_WITH_MILLIS, requestDatetime));
    }

    public static String genReportFileNameForMailMerge(GenRequest request) {
        Assert.notNull(request, "GenRequest parameter can not be null.");
        Assert.notNull(request.getParameterAsString("templateID"), "GenRequest templateID parameter can not be null.");

        Date requestDatetime = request.getRequestDatetime() != null ? request.getRequestDatetime() : Sys.getServerDate();
        String userId = null;
        if (StringUtils.isNotBlank(request.getRequestUserId())) {
            userId = StringUtils.trimToEmpty(request.getRequestUserId());
        } else {
            userId = "SYSTEM";
        }

        return StringUtils.upperCase(request.getParameterAsString("templateID") + "_" + userId + "_" + DateUtils.formatDateTime(DateFormatConstant.DATETIME_WITHOUT_SEPARATOR_LONG_WITH_MILLIS, requestDatetime));
    }

    public static Map<String, Object> getParamMap(GenRequest request) {
        return request.getParams();
    }

    /**
     * Convert a comma separated(like "aaa,bbb,ccc") string into a collection.
     *
     * @param valueStr a "aaa,bbb,ccc"-like string.
     * @return the collection.
     */
    public static Collection<String> toCollection(String valueStr) {
        return StringUtils.isBlank(valueStr) ? null : Arrays.asList(valueStr.trim().split(","));
    }

    /**
     * Convert a Collection&lt;String&gt; to  a Collection&lt;Integer&gt;.
     *
     * @param strCollec given Collection&lt;String&gt collection.
     * @return retrun the Collection&lt;Integer&gt;.
     */
    public static Collection<Integer> toIntCollection(Collection<String> strCollec) {
        Collection<Integer> ints = null;
        if (strCollec != null && strCollec.size() > 0) {
            ints = new ArrayList<Integer>();
            for (String s : strCollec) {
                if (StringUtils.isBlank(s)) {
                    ints.add(null);
                } else {
                    ints.add(Integer.parseInt(s));
                }
            }
        }
        return ints;
    }

    /**
     * Convert all the blank string values in parameter map to null.<br/>
     * Here the blank strings are referred to as strings like whitespace or
     * empty ("").
     *
     * @param params Map which may contains blank string values.
     */
    public static void blankToNull(Map<String, Object> params) {
        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof String) {
                    if (StringUtils.isBlank(String.valueOf(entry.getValue()))) {
                        params.put(entry.getKey(), null);
                    }
                }
            }
        }
    }

    /**
     * Convert a CSV file to excel format object.<br/>
     * Here will return a org.apache.poi.ss.usermodel.Workbook object.
     *
     * @param csvFile Given CSV file absolute path.
     * @return Converted work book object.
     * @throws IOException
     */
    public static HSSFWorkbook csvToExcelObject(String csvFile) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        StringBuilder strBuilder = new StringBuilder("");
        String tempLine = null;
        int rowIndex = 0; // Store row index number
        int maxCol = 0; // Store max column number

        while ((tempLine = br.readLine()) != null) {
            strBuilder.append(tempLine);

            // Check whether the temporal line is a completed CSV line
            int times = StringUtils.countMatches(strBuilder.toString(), "\"");
            if (times % 2 != 0) {
                // A single data block warped by quotes were separated
                // into different lines,
                // need to read the rest part of the data block.
                strBuilder.append("\n");
                continue;
            }

            // Split the CSV line into string array
            String[] rowData = null;
            StrTokenizer tokenizer = StrTokenizer.getCSVInstance(strBuilder.toString());
            rowData = tokenizer.getTokenArray();

            // Update the maximum column number
            if (maxCol < rowData.length) {
                maxCol = rowData.length;
            }

            HSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;

            for (int i = 0; i < rowData.length; i++) {
                HSSFCell cell = row.createCell(columnIndex++, HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(rowData[i]);
                cell.setCellStyle(style);
            }
            // Clear the string builder for next new line
            strBuilder.delete(0, strBuilder.length());
        }

        br.close();

        // Set the width of cell automatically same to the width of cell
        // value
        if (maxCol != 0) {
            for (int i = 0; i < maxCol; i++) {
                sheet.autoSizeColumn(i);
            }
        }

        return workbook;
    }


    /**
     * Reset the report result object.
     *
     * @param result
     */
    public static void resetResult(GenResult result) {
        if (result != null) {
            result.setLogId(null);
            result.setMessage(null);
            result.setReportFile(null);
            result.setReportId(null);
            result.setReturnCode(null);
            result.setStatus(null);
        }
    }

    public static void testParams(Map<String, Object> params) {
        for (Entry<String, Object> e : params.entrySet()) {
            LOG.debug(e.getKey() + "=" + e.getValue());
        }
    }

    /**
     * Split the source string with line break.
     *
     * @param srcStr
     * @return
     */
    public static List<String> splitWithBr(String src) {
        if (src == null) {
            return null;
        } else {
            List<String> list = new ArrayList<String>();
            String str = src.trim();
            if (str.length() < 40) {
                list.add(str);
            } else {
                int splitPos = src.indexOf(" ", 40);
                if (splitPos != -1) {
                    list.add(str.substring(0, splitPos));
                    list.add(str.substring(splitPos));
                } else {
                    list.add(str);
                }
            }
            return list;
        }
    }

    /**
     * Rename the specified srcPath to the newName end with given extension.
     * Given srcPath must point to an existed file.
     *
     * @param srcPath   given source file path
     * @param newName   new name
     * @param extension file extension
     * @return the new file's path.
     * @throws IOException
     */
    public static String rename(String srcPath, String newName, String extension) throws IOException {
        if (srcPath == null) {
            throw new NullPointerException("Source file path can not be null.");
        }
        if (newName == null) {
            throw new NullPointerException("New file name can not be null.");
        }
        File src = new File(srcPath);
        if (!src.exists() || src.isDirectory()) {
            throw new IllegalArgumentException("The file specified by the source path \"" + srcPath + "\" does not exist or is a directory.");
        }
        String destPath = srcPath.substring(0, srcPath.lastIndexOf(src.getName())) + newName + "." + extension;
        File f = new File(destPath);
        if (f.exists()) {
            f.delete();
        }
        src.renameTo(new File(destPath));
        return f.getCanonicalPath();
    }

    /**
     * Copy the source file to a new file in the same parent directory.
     *
     * @param src     source file
     * @param newName new file's name
     * @return the new copied file
     * @throws IOException
     */
    public static File copy(File src, String newName) throws IOException {
        if (src == null) {
            throw new NullPointerException("The source file can not be null");
        }
        String filePath = src.getCanonicalPath().replaceAll("\\\\", "/");
        String dirPath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        File destFile = new File(dirPath, newName);
        FileInputStream fin = new FileInputStream(src);
        FileOutputStream fout = new FileOutputStream(destFile);
        byte[] temp = new byte[1024];
        int byteNum = 0;
        while ((byteNum = fin.read(temp)) > 0) {
            fout.write(temp, 0, byteNum);
        }
        fout.flush();
        fin.close();
        fout.close();
        return destFile;
    }

    /**
     * Copy the source file to a new file in the same parent directory.
     *
     * @param src     source file
     * @param newName new file's name
     * @return the new copied file
     * @throws IOException
     */
    public static File copy(String srcPath, String newName) throws IOException {
        if (srcPath == null) {
            throw new NullPointerException("The source file path can not be null : " + srcPath);
        }
        File src = new File(srcPath);
        if (!src.exists()) {
            throw new IllegalArgumentException("The given source file does not exist : " + srcPath);
        }
        String filePath = src.getCanonicalPath().replaceAll("\\\\", "/");
        String dirPath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        File destFile = new File(dirPath, newName);
        FileInputStream fin = new FileInputStream(src);
        FileOutputStream fout = new FileOutputStream(destFile);
        byte[] temp = new byte[1024];
        int byteNum = 0;
        while ((byteNum = fin.read(temp)) > 0) {
            fout.write(temp, 0, byteNum);
        }
        fout.flush();
        fin.close();
        fout.close();
        return destFile;
    }

    /**
     * Format the given file's path.
     *
     * @param f given file.
     * @return formatted file path.
     * @throws IOException
     */
    public static String fmtPath(File f) throws IOException {
        if (f == null) {
            throw new NullPointerException("Given file can not be null");
        }
        return f.getCanonicalPath().replaceAll("\\\\", "/");
    }

    public static void main(String... strings) throws IOException {
        // String str="\",12354,1324,134,\"";
        String str1 = "\"95882122,63235715\"";
        String rgx = "\"(,?[0-9]+,?)+\"";
        System.out.println(str1);
        System.out.println(rgx);
        System.out.println(str1.matches(rgx));
    }
}
