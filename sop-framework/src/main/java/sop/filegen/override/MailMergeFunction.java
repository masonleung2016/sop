package sop.filegen.override;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sop.filegen.FileGenerationException;
import sop.filegen.FileGeneratorConstant;
import sop.filegen.GenFile;
import sop.filegen.GenFileFunction;
import sop.filegen.GenRequest;
import sop.filegen.GenResult;
import sop.filegen.generator.impl.MailMergeEnginelService;
import sop.util.Sys;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:34
 * @Package: sop.filegen.override
 */

@GenFile("SYSTEM-MAIL-MERGE")
@Component
public class MailMergeFunction implements GenFileFunction {

    @Autowired
    private MailMergeEnginelService mailMergeService;
    //@Autowired
    //private TccXREFRepository tccXREFRepository;

    @Override
    public GenResult generateFile(GenRequest request) throws FileGenerationException {
        String screenID = request.getParameterAsString("screenID");
        String templateID = request.getParameterAsString("templateID");

        GenResult result = new GenResult();
        result.setStartTime(Sys.getServerDate().getTime());

        String f = mailMergeService.toMailMerge(screenID, templateID, request, result);
        result.setReportFile(f);
        result.setReportId("SYSTEM-MAIL-MERGE");
        result.setMessage(templateID + " generated.");
        result.setStatus(FileGeneratorConstant.REPORT_STATUS_COMPLETED);
        result.setReturnCode(FileGeneratorConstant.SUCCESS);
        result.setEndTime(Sys.getServerDate().getTime());
        return result;

    }

    @Override
    public void initCommonInfoForGenDOC(GenRequest genReq)
            throws FileGenerationException {
        // TODO Auto-generated method stub
    }

//	@Override
//	public void initCommonInfoForGenDOC(GenRequest request) throws FileGenerationException {
//		String schameType="TAVA";
//		//get form casekey
//		CaseKeyAndCustKey caseKeyAndCustKey = (CaseKeyAndCustKey)request.getParameter("caseKeyAndCustKey");
//		Integer caseKey = 1;
//		if(caseKeyAndCustKey != null){
//			caseKey = caseKeyAndCustKey.getCaseKey();
//		}
//		//caseInforHeader
//		CaseInformationHeader caseInformationHeader = (CaseInformationHeader)request.getParameter("caseInforHeader");
//		//CaseInformationHeader caseInformationHeader = (CaseInformationHeader)ConversationSessionUtil.getCaseInforHeader(request);
//		//List<CaseCustomerXREF> schameTypeList = tccXREFRepository.findByIdCaseKey(caseKey);
//			if(caseInformationHeader != null && caseInformationHeader.getCaseNumObj().getMid() != null){
//			if(caseInformationHeader.getCaseNumObj().getMid()!=""){
//				schameType = caseInformationHeader.getCaseNumObj().getMid().trim();
//				if(schameType.equals("P")||schameType.equals("C"))schameType = "CSSA";
//				if(schameType.equals("S"))schameType = "SSA";
//				if(schameType.equals("A"))schameType = "TAVA";
//				if(schameType.equals("I"))schameType = "CLE";
//				if(schameType.equals("E"))schameType = "ERF";
//				if(schameType.equals("F"))schameType = "FA";
//			}else{
//				schameType = "TAVA";
//			}
//		}
//		String templateID = schameType + "_Common";
//		String screenID=templateID;
//		try{
//			mailMergeService.initCommonInfoForGenDOC(caseKey,screenID,templateID,request);
//		}catch(Exception e){
//			throw new FileGenerationException(e);
//		}
//	}
}
