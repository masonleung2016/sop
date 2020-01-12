package sop.filegen;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sop.constant.DateFormatConstant;
import sop.filegen.generator.GenFileAnnotationBeanPostProcessor;
import sop.filegen.generator.impl.DocxFileGenerator;
import sop.filegen.generator.impl.JrCsvFileGenerator;
import sop.filegen.generator.impl.JrHtmlFileGenerator;
import sop.filegen.generator.impl.JrJdbcCsvFileGenerator;
import sop.filegen.generator.impl.JrJdbcHtmlFileGenerator;
import sop.filegen.generator.impl.JrJdbcPdfFileGenerator;
import sop.filegen.generator.impl.JrJdbcTxtFileGenerator;
import sop.filegen.generator.impl.JrJdbcXlsFileGenerator;
import sop.filegen.generator.impl.JrJdbcXlsxFileGenerator;
import sop.filegen.generator.impl.JrPdfFileGenerator;
import sop.filegen.generator.impl.JrTxtFileGenerator;
import sop.filegen.generator.impl.JrXlsFileGenerator;
import sop.filegen.generator.impl.JrXlsxFileGenerator;
import sop.util.DateUtils;
import sop.util.Sys;
import sop.utils.Common;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:37
 * @Package: sop.filegen
 */

public class GenFileDispatcherEndpoint implements GenFileFunction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GenFileAnnotationBeanPostProcessor genFunctionAnnotationBeanPostProcessor;

    private String outputFolder;
    private String downloadURL;
    @Autowired
    private DataSource dataSource;

    public void setOutputFolder(String outputFolder) {
        Map<String, String> tempPropertiesMap = Common.getProperties("/temp.properties");
        String tempOutputFolder = tempPropertiesMap.get("outputFolder");
        this.outputFolder = tempOutputFolder;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    @Override
    public GenResult generateFile(GenRequest request) throws FileGenerationException {
        logger.info("Received GenRequest: reportId={}, outputType={}", request.getReportId(), request.getOutputType());
        Object o = genFunctionAnnotationBeanPostProcessor.getGenFileMap().get(request.getReportId());
        GenResult result = new GenResult();
        final String today = DateUtils.formatDateTime(DateFormatConstant.DATE_WITHOUT_SEPARATOR_SHORT, Sys.getServerDate());
        if (o == null) {
            logger.debug("Process with default handler.");
            if ("pdf".equalsIgnoreCase(request.getOutputType())) {
                if ("jdbc".equalsIgnoreCase(request.getFillType())) {
                    JrJdbcPdfFileGenerator fg = new JrJdbcPdfFileGenerator(dataSource);
                    fg.setOutputFolder(outputFolder + "/pdf/" + today);
                    result = fg.generate(request);
                } else {
                    JrPdfFileGenerator fg = new JrPdfFileGenerator();
                    fg.setOutputFolder(outputFolder + "/pdf/" + today);
                    result = fg.generate(request);
                }
            } else if ("docx".equalsIgnoreCase(request.getOutputType())) {
                DocxFileGenerator fg = new DocxFileGenerator();
                fg.setOutputFolder(outputFolder + "/docx/" + today);
                result = fg.generate(request);
            } else if ("csv".equalsIgnoreCase(request.getOutputType())) {
                if ("jdbc".equalsIgnoreCase(request.getFillType())) {
                    JrJdbcCsvFileGenerator fg = new JrJdbcCsvFileGenerator(dataSource);
                    fg.setOutputFolder(outputFolder + "/csv/" + today);
                    result = fg.generate(request);
                } else {
                    JrCsvFileGenerator fg = new JrCsvFileGenerator();
                    fg.setOutputFolder(outputFolder + "/csv/" + today);
                    result = fg.generate(request);
                }
            } else if ("xls".equalsIgnoreCase(request.getOutputType())) {
                if ("jdbc".equalsIgnoreCase(request.getFillType())) {
                    JrJdbcXlsFileGenerator fg = new JrJdbcXlsFileGenerator(dataSource);
                    fg.setOutputFolder(outputFolder + "/xls/" + today);
                    result = fg.generate(request);
                } else {
                    JrXlsFileGenerator fg = new JrXlsFileGenerator();
                    fg.setOutputFolder(outputFolder + "/xls/" + today);
                    result = fg.generate(request);
                }
            } else if ("xlsx".equalsIgnoreCase(request.getOutputType())) {
                if ("jdbc".equalsIgnoreCase(request.getFillType())) {
                    JrJdbcXlsxFileGenerator fg = new JrJdbcXlsxFileGenerator(dataSource);
                    fg.setOutputFolder(outputFolder + "/xlsx/" + today);
                    result = fg.generate(request);
                } else {
                    JrXlsxFileGenerator fg = new JrXlsxFileGenerator();
                    fg.setOutputFolder(outputFolder + "/xlsx/" + today);
                    result = fg.generate(request);
                }
            } else if ("txt".equalsIgnoreCase(request.getOutputType())) {
                if ("jdbc".equalsIgnoreCase(request.getFillType())) {
                    JrJdbcTxtFileGenerator fg = new JrJdbcTxtFileGenerator(dataSource);
                    fg.setOutputFolder(outputFolder + "/txt/" + today);
                    result = fg.generate(request);
                } else {
                    JrTxtFileGenerator fg = new JrTxtFileGenerator();
                    fg.setOutputFolder(outputFolder + "/txt/" + today);
                    result = fg.generate(request);
                }
            } else if ("html".equalsIgnoreCase(request.getOutputType())) {
                if ("jdbc".equalsIgnoreCase(request.getFillType())) {
                    JrJdbcHtmlFileGenerator fg = new JrJdbcHtmlFileGenerator(dataSource);
                    fg.setOutputFolder(outputFolder + "/html/" + today);
                    result = fg.generate(request);
                } else {
                    JrHtmlFileGenerator fg = new JrHtmlFileGenerator();
                    fg.setOutputFolder(outputFolder + "/html/" + today);
                    result = fg.generate(request);
                }
            } else {
                logger.warn("System can not find corresponding GenFile handler.");
                result.setMessage("System can not find corresponding Generator with fillType = " + request.getFillType() + " and outputType = " + request.getOutputType());
            }
        } else {
            GenFileFunction func = (GenFileFunction) o;
            logger.debug("Process with handler {}.", func);
            result = func.generateFile(request);
        }

        if (StringUtils.isNotBlank(result.getReportFile())) {
            String relativePath = getRelativePath(result.getReportFile());
            if (downloadURL.endsWith("/") && relativePath.startsWith("/")) {
                result.setRemoteDownloadURL(StringUtils.removeEnd(downloadURL, "/") + relativePath);
            } else {
                result.setRemoteDownloadURL(downloadURL + relativePath);
            }
        }

        return result;
    }

    private String getRelativePath(String fullPath) {
        String op = outputFolder;
        return fullPath.replaceFirst(op, "");
    }

    @Override
    public void initCommonInfoForGenDOC(GenRequest request)
            throws FileGenerationException {
        Object o = genFunctionAnnotationBeanPostProcessor.getGenFileMap().get(request.getReportId());
        GenFileFunction func = (GenFileFunction) o;
        logger.debug("Process with handler {}.", func);
        func.initCommonInfoForGenDOC(request);
    }

}
