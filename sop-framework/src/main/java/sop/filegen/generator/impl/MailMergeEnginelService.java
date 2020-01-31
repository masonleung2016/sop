package sop.filegen.generator.impl;

import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:57
 * @Package: sop.filegen.generator.impl
 */

public interface MailMergeEnginelService {

    String toMailMerge(String screenID, String templateID, GenRequest request, GenResult response);

    String toMailMerge(String screenID, String templateID, GenRequest request, GenResult response, Object object, FieldsMetadata metadata);

    void initCommonInfoForGenDOC(Integer caseKey, String screenID, String templateID, GenRequest request);

}
