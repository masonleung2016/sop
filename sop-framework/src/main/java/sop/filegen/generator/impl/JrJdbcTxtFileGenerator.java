package sop.filegen.generator.impl;

import javax.sql.DataSource;

import sop.filegen.FileGenerationException;
import sop.filegen.FileGeneratorConstant;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:52
 * @Package: sop.filegen.generator.impl
 */

public class JrJdbcTxtFileGenerator extends BaseFileGenerator {

    private DataSource ds;

    public JrJdbcTxtFileGenerator(DataSource ds) {
        this.ds = ds;
    }

    @Override
    protected GenResult generateFile(GenRequest request) throws FileGenerationException {

        FileGeneratorUtils.makeDirIfNotExists(getOutputFolder());

        GenResult result = new GenResult();
        try {
            final String reportFileName = FileGeneratorUtils.genReportFileName(request);
            String outFile = FileGeneratorUtils.getReportOutputFullPath(getOutputFolder(), reportFileName, ".txt");
            JrReportEngine instance = JrReportEngine.getInstance();
            instance.setDataSource(ds);
            outFile = instance.exportTxtWithSQL(request.getReportId(), FileGeneratorUtils.getParamMap(request), outFile);
            result.setReturnCode(FileGeneratorConstant.SUCCESS);
            result.setReportFile(outFile);
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
