package sop.filegen.generator.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map.Entry;

import fr.opensagres.xdocreport.core.document.SyntaxKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import sop.filegen.FileGenerationException;
import sop.filegen.FileGeneratorConstant;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:46
 * @Package: sop.filegen.generator.impl
 */


public class DocxFileGenerator extends BaseFileGenerator {

    @Override
    protected GenResult generateFile(GenRequest request) throws FileGenerationException {
        GenResult rs = new GenResult();
        try {

            final String reportFileName = FileGeneratorUtils.genReportFileName(request);
            String outFile = FileGeneratorUtils.getReportOutputFullPath(getOutputFolder(), reportFileName, ".docx");

            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
            InputStream in = new FileInputStream(FileGeneratorUtils.getDocxTemplate(request.getReportId()));
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);


            // 3) Create context Java model
            IContext context = report.createContext();
            // Register project
            for (Entry<String, Object> entry : request.getParams().entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }

            FieldsMetadata metadata = new FieldsMetadata();
            for (String fn : request.getFieldMeta()) {
                metadata.addFieldAsList(fn);
            }

            for (String fn : request.getHtmlFields()) {
                metadata.addFieldAsTextStyling(fn, SyntaxKind.Html);
            }

            report.setFieldsMetadata(metadata);

            // 4) Generate report by merging Java model with the Docx
            OutputStream out = new FileOutputStream(outFile);
            report.process(context, out);
            rs.setReturnCode(FileGeneratorConstant.SUCCESS);
            rs.setReportFile(outFile);

        } catch (Exception e) {
            logger.error("", e);
            throw new FileGenerationException(e.getMessage());
        }

        return rs;
    }

}

