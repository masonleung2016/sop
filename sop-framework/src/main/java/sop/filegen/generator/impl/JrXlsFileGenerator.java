package sop.filegen.generator.impl;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sop.filegen.FileGenerationException;
import sop.filegen.FileGeneratorConstant;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:56
 * @Package: sop.filegen.generator.impl
 */

public class JrXlsFileGenerator extends BaseFileGenerator {

    @Override
    protected GenResult generateFile(GenRequest request) throws FileGenerationException {

        FileGeneratorUtils.makeDirIfNotExists(getOutputFolder());

        GenResult result = new GenResult();
        try {
            final String reportFileName = FileGeneratorUtils.genReportFileName(request);
            String outFile = FileGeneratorUtils.getReportOutputFullPath(getOutputFolder(), reportFileName, ".xls");

            List<Object> dataList = request.getDataList();
            JRDataSource jrds = new JRBeanCollectionDataSource(dataList);
            outFile = JrReportEngine.getInstance().exportXlsWithJRDatasource(request.getReportId(), FileGeneratorUtils.getParamMap(request), jrds, outFile);
            result.setReturnCode(FileGeneratorConstant.SUCCESS);
            result.setReportFile(outFile);

        } catch (Exception e) {
            logger.error("XLS generation error:", e);
            throw new FileGenerationException(e.getMessage());
        } finally {
            result.setReportId(request.getReportId());
            result.setStatus(FileGeneratorConstant.REPORT_STATUS_COMPLETED);
        }
        return result;
    }
}
