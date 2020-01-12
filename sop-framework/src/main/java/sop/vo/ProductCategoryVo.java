package sop.vo;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:47
 * @Package: sop.vo
 */


public class ProductCategoryVo {
    /**
     * table comp
     */
    private String catCode;
    private String encodeCatCode;
    private String catDesc;

    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
        this.encodeCatCode = Base64.encode(catCode, "utf-8");
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public String getEncodeCatCode() {
        return encodeCatCode;
    }

    public void setEncodeCatCode(String encodeCatCode) {
        this.encodeCatCode = encodeCatCode;
    }
}
