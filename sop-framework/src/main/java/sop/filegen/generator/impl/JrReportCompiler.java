package sop.filegen.generator.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:53
 * @Package: sop.filegen.generator.impl
 */


public class JrReportCompiler {
    private static final Logger logger = LoggerFactory.getLogger(JrReportCompiler.class);
    private static Map COMPILER_SETTINGS = new HashMap();

    static {
        COMPILER_SETTINGS.put(JRParameter.REPORT_LOCALE, Locale.SIMPLIFIED_CHINESE);
    }

    public static void compileToFile(String templateId, String destFileId, Map params) throws JRException {
        JasperCompileManager.compileReportToFile(templateId, destFileId);
    }

    public static void compileToFileWhenNeccessary(String templateFolder, Map<String, String> jasperCaches, String templateId) {
        File dir = new File(templateFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File jrxmlFolder = new File(templateFolder);
        if (!jrxmlFolder.exists()) {
            jrxmlFolder.mkdirs();
        }
        File jasperFolder = new File(templateFolder + "/jasper");
        if (!jasperFolder.exists()) {
            jasperFolder.mkdirs();
        }
        FilenameFilter jaFilter = new WildcardFileFilter(templateId + "*.jasper");
        FilenameFilter jrFilter = new WildcardFileFilter(templateId + "*.jrxml");

        File[] jasperFiles = jrxmlFolder.listFiles(jaFilter);
        File[] jrxmlFiles = jrxmlFolder.listFiles(jrFilter);

        //there may be many files starts with templateId*.jrxml
        for (File file : jrxmlFiles) {
            final String tempF = StringUtils.replace(file.getName(), ".jrxml", "");
            if (!jasperCaches.containsKey(tempF) || ArrayUtils.isEmpty(jasperFiles)) {
                String path = file.getAbsolutePath();
                logger.info("Found jasper JRXML xml file {}", path);
                final String destFile = jasperFolder + "/" + tempF + ".jasper";
                try {
                    compileToFile(path, destFile, COMPILER_SETTINGS);
                    logger.info("Successfully compiled report template {}.", destFile);
                    jasperCaches.put(tempF, destFile);
                } catch (JRException e) {
                    logger.error("Failed to compile report template {}, error: {}.", path, e);
                }
            }


        }


    }

    public static void compileAllTemplates(String templateFolder, Map<String, String> jasperCaches) {
        File dir = new File(templateFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File jrxmlFolder = new File(templateFolder);
        if (!jrxmlFolder.exists()) {
            jrxmlFolder.mkdirs();
        }
        File jasperFolder = new File(templateFolder + "/jasper");
        if (!jasperFolder.exists()) {
            jasperFolder.mkdirs();
        }

        logger.info("Start processing jasperreport templates under folder: {}", templateFolder);

        if (jrxmlFolder.isDirectory()) {
            List<String> failList = new ArrayList<String>(0);
            FilenameFilter jrFilter = new WildcardFileFilter("*.jrxml");
            File[] files = jrxmlFolder.listFiles(jrFilter);
            for (File file : files) {
                final String reportId = StringUtils.replace(file.getName(), ".jrxml", "");
                compileToFileWhenNeccessary(templateFolder, jasperCaches, reportId);
            }

            if (!failList.isEmpty()) {
                logger.warn("Total {} files failed to compile: {}", failList.size(), failList);
            }
        } else {
            logger.warn("JasperReport template folder {} was not created.", jrxmlFolder);
        }


    }

    public static void main(String[] args) throws JRException {
        String templateId = "D:\\Temp\\report\\jrxml\\test.jrxml";
        String destFileId = "D:\\Temp\\report\\jasper\\test.jasper";
        JasperCompileManager.compileReportToFile(templateId, destFileId);
    }
}
