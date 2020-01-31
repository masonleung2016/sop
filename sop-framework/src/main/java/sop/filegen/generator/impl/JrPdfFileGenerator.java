package sop.filegen.generator.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sop.filegen.BarcodeGenRequest;
import sop.filegen.FileGenerationException;
import sop.filegen.FileGeneratorConstant;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:53
 * @Package: sop.filegen.generator.impl
 */

public class JrPdfFileGenerator extends BaseFileGenerator {

    @Override
    protected GenResult generateFile(GenRequest request) throws FileGenerationException {

        FileGeneratorUtils.makeDirIfNotExists(getOutputFolder());

        GenResult result = new GenResult();
        try {
            final String reportFileName = FileGeneratorUtils.genReportFileName(request);
            String outFile = FileGeneratorUtils.getReportOutputFullPath(getOutputFolder(), reportFileName, ".pdf");
            String outFile2 = FileGeneratorUtils.getReportOutputFullPath(getOutputFolder(), reportFileName + "_barcode", ".pdf");

            List<Object> dataList = request.getDataList();
            JRDataSource jrds = new JRBeanCollectionDataSource(dataList);
            outFile = JrReportEngine.getInstance().exportPdfWithJRDatasource(request.getReportId(), FileGeneratorUtils.getParamMap(request), jrds, outFile);
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
