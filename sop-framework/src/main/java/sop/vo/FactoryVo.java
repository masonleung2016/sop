package sop.vo;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:38
 * @Package: sop.vo
 */


public class FactoryVo {
    private String suCode;
    private String encodeSuCode;
    private String suName;
    private String suChn;
    private String suConPer;

    public String getSuCode() {
        return suCode;
    }

    public void setSuCode(String suCode) {
        this.suCode = suCode;
        this.encodeSuCode = Base64.encode(suCode, "utf-8");
    }

    public String getEncodeSuCode() {
        return encodeSuCode;
    }

    public void setEncodeSuCode(String encodeSuCode) {
        this.encodeSuCode = encodeSuCode;
    }

    public String getSuName() {
        return suName;
    }

    public void setSuName(String suName) {
        this.suName = suName;
    }

    public String getSuChn() {
        return suChn;
    }

    public void setSuChn(String suChn) {
        this.suChn = suChn;
    }

    public String getSuConPer() {
        return suConPer;
    }

    public void setSuConPer(String suConPer) {
        this.suConPer = suConPer;
    }
}
