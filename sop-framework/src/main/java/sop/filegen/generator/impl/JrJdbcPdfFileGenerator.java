package sop.filegen.generator.impl;

import java.io.File;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;

import sop.filegen.BarcodeGenRequest;
import sop.filegen.FileGenerationException;
import sop.filegen.FileGeneratorConstant;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:50
 * @Package: sop.filegen.generator.impl
 */


public class JrJdbcPdfFileGenerator extends BaseFileGenerator {

    private DataSource ds;

    public JrJdbcPdfFileGenerator(DataSource ds) {
        this.ds = ds;
    }

    @Override
    protected GenResult generateFile(GenRequest request) throws FileGenerationException {

        FileGeneratorUtils.makeDirIfNotExists(getOutputFolder());

        GenResult result = new GenResult();
        try {
            final String reportFileName = FileGeneratorUtils.genReportFileName(request);
            String outFile = FileGeneratorUtils.getReportOutputFullPath(getOutputFolder(), reportFileName, ".pdf");
            String outFile2 = FileGeneratorUtils.getReportOutputFullPath(getOutputFolder(), reportFileName + "_barcode", ".pdf");
            JrReportEngine instance = JrReportEngine.getInstance();
            instance.setDataSource(ds);
            outFile = instance.exportPdfWithSQL(request.getReportId(), FileGeneratorUtils.getParamMap(request), outFile);
            result.setReturnCode(FileGeneratorConstant.SUCCESS);
            result.setReportFile(outFile);

            //print barcode
            if (request.isPrintBarCode()) {
                if (request instanceof BarcodeGenRequest) {
                    BarcodeGenRequest req = (BarcodeGenRequest) request;
                    OmrBarcodeGenerator.printBarcode(outFile, outFile2, req.getLeft(), req.getTop(), req.getWidth(), req.getHeight());
                    result.setReportFile(outFile2);
                    File orgFile = new File(outFile);
                    FileUtils.deleteQuietly(orgFile);
                } else {
                    OmrBarcodeGenerator.printBarcode(outFile, outFile2);
                    result.setReportFile(outFile2);
                    File orgFile = new File(outFile);
                    FileUtils.deleteQuietly(orgFile);
                }

            }
        } catch (Exception e) {
            logger.error("PDF generation error:", e);
            throw new FileGenerationException(e.getMessage());
        } finally {
            result.setReportId(request.getReportId());
            result.setStatus(FileGeneratorConstant.REPORT_STATUS_COMPLETED);
        }
        return result;
    }


}
