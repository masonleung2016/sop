package sop.vo;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:38
 * @Package: sop.vo
 */

public class GlInterfaceVo {
    
    private Integer id;
    /**
     * gl_inf_code
     */
    private String glInfCode;

    private String encodeGlInfCode;
    /**
     * gl_inf_desc
     */
    private String glInfDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGlInfCode() {
        return glInfCode;
    }

    public void setGlInfCode(String glInfCode) {
        this.glInfCode = glInfCode;
        this.encodeGlInfCode = Base64.encode(glInfCode, "utf-8");
    }

    public String getGlInfDesc() {
        return glInfDesc;
    }

    public void setGlInfDesc(String glInfDesc) {
        this.glInfDesc = glInfDesc;
    }

    public String getEncodeGlInfCode() {
        return encodeGlInfCode;
    }

    public void setEncodeGlInfCode(String encodeGlInfCode) {
        this.encodeGlInfCode = encodeGlInfCode;
    }
}
