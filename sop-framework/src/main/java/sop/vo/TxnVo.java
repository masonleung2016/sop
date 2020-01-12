package sop.vo;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:40
 * @Package: sop.vo
 */


public class TxnVo {
    private Integer id;
    private String txtCode;
    private String encodeTxtCode;
    private String txtDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxtCode() {
        return txtCode;
    }

    public void setTxtCode(String txtCode) {
        this.txtCode = txtCode;
        this.encodeTxtCode = Base64.encode(txtCode, "utf-8");
    }

    public String getEncodeTxtCode() {
        return encodeTxtCode;
    }

    public void setEncodeTxtCode(String encodeTxtCode) {
        this.encodeTxtCode = encodeTxtCode;
    }

    public String getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(String txtDesc) {
        this.txtDesc = txtDesc;
    }
}
