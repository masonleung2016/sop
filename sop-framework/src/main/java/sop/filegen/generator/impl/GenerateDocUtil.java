package sop.filegen.generator.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import sop.constant.DocxTempalte;
import sop.util.io.FileUtil;
import sop.vo.PrintDTO;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:48
 * @Package: sop.filegen.generator.impl
 */

@Component(value = "generateDocUtil")
public class GenerateDocUtil {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void generateDoc(HttpServletResponse response, String fileName, PrintDTO printDTO) {
        try {
            logger.debug(fileName + "-----------" + includeTemplate(fileName + ".docx"));
            if (fileName != null && includeTemplate(fileName + ".docx")) {
                logger.debug("-------generate123123123123123123123----------");
                // 1) Load Docx file by filling Velocity template engine and cache
                // it to the registry
                InputStream in = new FileInputStream(getDocTemplate(fileName + ".docx"));
                IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
                // 3) Create context Java model
                IContext context = report.createContext();
                // Register project
                context.put("printDTO", printDTO);
                // 4) Generate report by merging Java model with the Docx
                String str = System.currentTimeMillis() + "";
                String outPath = getTempFolder() + "/" + fileName + str + ".docx";
                //String outPath = "d:"+ "/"+fileName+str+"docx";
                OutputStream out = new FileOutputStream(new File(outPath));
                report.process(context, out);
                downloadFile(response, new File(outPath));
            }

        } catch (IOException e) {
            logger.error("failed to download file.", e);

        } catch (XDocReportException e) {
            logger.error("failed to generate docx.", e);
        }
    }

    private String getDocTemplate(String fileName) {
        return FileUtil.mergeSubDirectory(new String[]{getBaseConfigPath(), "/docxTemplates"}) + fileName;
    }

    public String getBaseConfigPath() {
        return System.getProperty("rrr.config.path");
    }

    public String getTempFolder() {
        return System.getProperty("java.io.tmpdir");
    }

    public boolean downloadFile(HttpServletResponse response, InputStream inputStream, String outputName) {
        try {
            final String type = FileUtil.getMimeType(inputStream);
            return FileUtil.downloadFile(response, inputStream, type, outputName);
        } catch (Exception e) {
            logger.error("File not found.", e);
            return false;
        }
    }

    public boolean downloadFile(HttpServletResponse response, File file) {
        try {
            FileInputStream fio = new FileInputStream(file);
            final String type = FileUtil.getMimeType(file);
            return FileUtil.downloadFile(response, fio, type, file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("File not found.", e);
            return false;
        }
    }

    private boolean includeTemplate(String template) {
        if (DocxTempalte.isTemplate(template)) {
            return true;
        }
        return false;
    }
}
