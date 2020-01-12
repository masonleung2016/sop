package sop.filegen.override;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map.Entry;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import sop.filegen.FileGenerationException;
import sop.filegen.FileGeneratorConstant;
import sop.filegen.GenFileFunction;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;
import sop.filegen.generator.impl.DocxFileGenerator;
import sop.filegen.generator.impl.FileGeneratorUtils;
import sop.util.Sys;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:34
 * @Package: sop.filegen.override
 */

public abstract class BaseDocxGenFileFunction extends DocxFileGenerator implements GenFileFunction {


    protected abstract void mergeContext(GenRequest request, GenResult response, IXDocReport report, IContext context);

    @Override
    public GenResult generateFile(GenRequest request) throws FileGenerationException {
        GenResult rs = new GenResult();
        InputStream in = null;
        OutputStream out = null;
        try {
            rs.setStartTime(Sys.getServerDate().getTime());

            final String reportFileName = FileGeneratorUtils.genReportFileName(request);
            String outFile = FileGeneratorUtils.getReportOutputFullPath(getOutputFolder(), reportFileName, "docx");

            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
            in = new FileInputStream(FileGeneratorUtils.getDocxTemplate(request.getReportId()));
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            // 3) Create context Java model
            IContext context = report.createContext();

            // Register project
            for (Entry<String, Object> entry : request.getParams().entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }

            // Merge Context
            mergeContext(request, rs, report, context);


            // 4) Generate report by merging Java model with the Docx
            out = new FileOutputStream(outFile);
            report.process(context, out);
            rs.setReturnCode(FileGeneratorConstant.SUCCESS);
            rs.setReportFile(outFile);

        } catch (Exception e) {
            throw new FileGenerationException(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("Error in closing inputStream.", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("Error in closing outputStream.", e);
                }
            }
        }

        return rs;
    }


}
