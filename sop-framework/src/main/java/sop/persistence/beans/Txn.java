package sop.persistence.beans;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:09
 * @Package: sop.persistence.beans
 */

public class Txn extends BaseBean {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String txtCode;
    
    private String txtDesc;
    
    private String txtIn;
    
    private String txtOut;
    
    private String txtDefLocIn;
    
    private String txtDefLocOut;

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
    }

    public String getTxtDesc() {
        return txtDesc;
    }

    public void setTxtDesc(String txtDesc) {
        this.txtDesc = txtDesc;
    }

    public String getTxtIn() {
        return txtIn;
    }

    public void setTxtIn(String txtIn) {
        this.txtIn = txtIn;
    }

    public String getTxtOut() {
        return txtOut;
    }

    public void setTxtOut(String txtOut) {
        this.txtOut = txtOut;
    }

    public String getTxtDefLocIn() {
        return txtDefLocIn;
    }

    public void setTxtDefLocIn(String txtDefLocIn) {
        this.txtDefLocIn = txtDefLocIn;
    }

    public String getTxtDefLocOut() {
        return txtDefLocOut;
    }

    public void setTxtDefLocOut(String txtDefLocOut) {
        this.txtDefLocOut = txtDefLocOut;
    }
}
