package sop.vo;

import java.util.List;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:35
 * @Package: sop.vo
 */

public class CustomerVo {
    
    private Integer id;
    
    private String cuCode;
    
    private String encodeCuCode;
    
    private String cuName;
    
    private String cuChn;
    
    private String cuConPer;
    
    private List<String> custAttns;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCuCode() {
        return cuCode;
    }

    public void setCuCode(String cuCode) {
        this.cuCode = cuCode;
        this.encodeCuCode = Base64.encode(cuCode, "utf-8");
    }

    public String getCuName() {
        return cuName;
    }

    public void setCuName(String cuName) {
        this.cuName = cuName;
    }

    public String getEncodeCuCode() {
        return encodeCuCode;
    }

    public void setEncodeCuCode(String encodeCuCode) {
        this.encodeCuCode = encodeCuCode;
    }

    public String getCuChn() {
        return cuChn;
    }

    public void setCuChn(String cuChn) {
        this.cuChn = cuChn;
    }

    public String getCuConPer() {
        return cuConPer;
    }

    public void setCuConPer(String cuConPer) {
        this.cuConPer = cuConPer;
    }

    public List<String> getCustAttns() {
        return custAttns;
    }

    public void setCustAttns(List<String> custAttns) {
        this.custAttns = custAttns;
    }
}
