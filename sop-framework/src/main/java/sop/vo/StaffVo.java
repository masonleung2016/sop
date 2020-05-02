package sop.vo;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:39
 * @Package: sop.vo
 */

public class StaffVo {
    
    private String sfCode;

    private String encodeSfCode;

    private String sfName;

    public String getSfCode() {
        return sfCode;
    }

    public void setSfCode(String sfCode) {
        
        this.sfCode = sfCode;
        
        this.encodeSfCode = Base64.encode(sfCode, "utf-8");
    }

    public String getSfName() {
        return sfName;
    }

    public void setSfName(String sfName) {
        this.sfName = sfName;
    }

    public String getEncodeSfCode() {
        return encodeSfCode;
    }

    public void setEncodeSfCode(String encodeSfCode) {
        this.encodeSfCode = encodeSfCode;
    }
}
