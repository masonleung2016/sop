package sop.persistence.beans;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:46
 * @Package: sop.persistence.beans
 */

public class CustAttn extends BaseBean {
    /**
     * table cust_attn
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * cu_code
     */
    private String cuCode;
    /**
     * cu_name
     */
    private String attention;

    private String encodeAttention;

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
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
        this.encodeAttention = Base64.encode(attention, "utf-8");
    }

    public String getEncodeAttention() {
        return encodeAttention;
    }

    public void setEncodeAttention(String encodeAttention) {
        this.encodeAttention = encodeAttention;
    }
}
