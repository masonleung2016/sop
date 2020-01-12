package sop.filegen.generator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sop.filegen.FileGenerationException;
import sop.filegen.FileGeneratorConstant;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;
import sop.filegen.generator.FileGenerator;
import sop.filegen.generator.GenLogService;
import sop.filegen.generator.GenLogServiceImpl;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:45
 * @Package: sop.filegen.generator.impl
 */

public abstract class BaseFileGenerator implements FileGenerator {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private Long logId = 0L;
    private String outputFolder = System.getProperty("java.tmp.dir");

    protected abstract GenResult generateFile(GenRequest request) throws FileGenerationException;

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    @Override
    public GenResult generate(GenRequest request) throws FileGenerationException {
        long start = System.currentTimeMillis();

        GenResult result = new GenResult();
        //record down the report request before further processing.
        if (logId == null) {
            logId = beforeGeneration(request, result);
        }

        //return when persisting record failed.
        if (logId == null) {
            return result;
        }


        //if no error.
        try {
            result = generateFile(request);
            result.setReturnCode(FileGeneratorConstant.SUCCESS);
            result.setReportId(request.getReportId());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Faild to generate file.", e);
            }
            result.setReturnCode(FileGeneratorConstant.FAILED);
            result.setReportFile(null);
            result.setMessage(e.getMessage());

        } finally {
            long end = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug("Time consumed: {} ms", end - start);
            }
            //getReportService().updateReportRequestWithResult(logId, result, (end-start));

            afterGeneration(request, result);
        }

        return result;

    }

    protected Long afterGeneration(GenRequest request, GenResult result) {
        result.setEndTime(System.currentTimeMillis());
        logger.debug("generation completed {}.", result.getReportFile());
        return 0L;
    }

    protected Long beforeGeneration(GenRequest request, GenResult result) {
        long logId = System.currentTimeMillis();
        result.setStartTime(System.currentTimeMillis());
        logger.debug("start generating: reportId= {},  outputType= {}.", request.getReportId(), request.getOutputType());


        return logId;
    }


    public GenLogService getReportService() {
        return new GenLogServiceImpl();
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }


}
