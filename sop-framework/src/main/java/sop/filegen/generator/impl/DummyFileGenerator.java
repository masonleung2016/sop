package sop.filegen.generator.impl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import sop.filegen.FileGeneratorConstant;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;
import sop.filegen.generator.FileGenerator;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:47
 * @Package: sop.filegen.generator.impl
 */

public class DummyFileGenerator implements FileGenerator {

    @Override
    public GenResult generate(GenRequest request) {
        
        GenResult result = new GenResult();
        
        result.setReturnCode(FileGeneratorConstant.FAILED);
        
        result.setMessage("System can not determine which report generator to be used. It may be due to empty reportType or outputType.");
        
        ToStringBuilder.reflectionToString(request, ToStringStyle.MULTI_LINE_STYLE);
        
        return result;
    }
}
