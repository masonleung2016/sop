package sop.constant;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:43
 * @Package: sop.constant
 */

public class DocxTempalte {
    public static String ssa = "SSA_SHEET.docx";
    
    public static String csa = "CSA_SHEET.docx";
    
    public static String cle = "CLE_SHEET.docx";
    
    public static String tava = "TAVA_SHEET.docx";
    
    public static String pcs = "PCS_PCBLetter.docx";
    
    public static String ssaApp = "SSA_APPLICATION.docx";
    
    public static String ssaREV = "SSA_REVIEW.docx";
    
    public static String pcsPCR = "PCS_PCR.docx";
    
    public static String csaApp = "CSSA_APPLICATION.docx";
    
    public static String csaSREV = "CSSA_SIMPLIFIED_REVIEW.docx";
    
    public static String csaCREV = "CSSA_COMPREHESIVE_REVIEW.docx";

    public static String pcs_0020 = "PCS_0020.docx";
    
    public static String pcs_0090 = "SC_PCS_0090.docx";
    
    public static String pcs_0160 = "SC_PCS_0160.docx";
    
    public static String pcs_0030 = "PCS_0030.docx";
    
    public static String pcs_0040 = "PCS_0040.docx";
    
    public static String ccd_2021 = "CCD_Search_Result.docx";
    
    public static String ccd_2022 = "CCD_CaseFileNumber_History.docx";
    
    public static String pcs_0230 = "SC_PCS_0230.docx";
    
    public static String pcs_0280 = "PCS_0280.docx";

    public static String csaMAF = "MEDICAL_ASSESSMENT_FORM.docx";

    public static boolean isTemplate(String template) {
        if (template.equals(csa) ||
                template.equals(ssa) ||
                template.equals(cle) ||
                template.equals(tava) ||
                template.equals(pcs) ||
                template.equals(pcsPCR) ||
                template.equals(ssaApp) ||
                template.equals(ssaREV) ||
                template.equals(csaApp) ||
                template.equals(csaSREV) ||
                template.equals(csaCREV) ||
                template.equals(pcs_0020) ||
                template.equals(pcs_0090) ||
                template.equals(pcs_0160) ||
                template.equals(pcs_0030) ||
                template.equals(pcs_0040) ||
                template.equals(pcs_0280) ||
                template.equals(ccd_2021) ||
                template.equals(ccd_2022) ||
                template.equals(csaMAF) ||
                template.equals(pcs_0230)) {

            return true;
        }
        return false;
    }
}
