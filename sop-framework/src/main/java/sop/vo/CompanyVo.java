package sop.vo;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:34
 * @Package: sop.vo
 */

public class CompanyVo {
    
    private Integer id;
    
    private String coCode;
    
    private String encodeCoCode;
    
    private String coName;
    
    private String coChn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
        this.encodeCoCode = Base64.encode(coCode, "utf-8");
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getCoChn() {
        return coChn;
    }

    public void setCoChn(String coChn) {
        this.coChn = coChn;
    }

    public String getEncodeCoCode() {
        return encodeCoCode;
    }

    public void setEncodeCoCode(String encodeCoCode) {
        this.encodeCoCode = encodeCoCode;
    }
}
