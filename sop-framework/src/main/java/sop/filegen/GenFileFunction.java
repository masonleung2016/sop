package sop.filegen;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:38
 * @Package: sop.filegen
 */

public interface GenFileFunction {

    GenResult generateFile(GenRequest request) throws FileGenerationException;


    //for init info
    void initCommonInfoForGenDOC(GenRequest genReq) throws FileGenerationException;
}
