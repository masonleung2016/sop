package sop.vo;

import java.math.BigDecimal;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:41
 * @Package: sop.vo
 */

public class UomVo {
    private Integer id;
    private String uCode;
    private String uDesc;
    private String uChn;
    private BigDecimal uRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getuCode() {
        return uCode;
    }

    public void setuCode(String uCode) {
        this.uCode = uCode;
    }

    public String getuDesc() {
        return uDesc;
    }

    public void setuDesc(String uDesc) {
        this.uDesc = uDesc;
    }

    public String getuChn() {
        return uChn;
    }

    public void setuChn(String uChn) {
        this.uChn = uChn;
    }

    public BigDecimal getuRate() {
        return uRate;
    }

    public void setuRate(BigDecimal uRate) {
        this.uRate = uRate;
    }
}
