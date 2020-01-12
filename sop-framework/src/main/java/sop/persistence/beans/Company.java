package sop.persistence.beans;

import java.util.Date;

/**
 * @Author: LCF
 * @Date: 2020/1/9 9:44
 * @Package: sop.persistence.beans
 */

public class Company extends BaseBean {
    /**
     * table comp
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String coCode;
    private String coName;
    private String coChn;
    private String coAdd;
    private String coChnAdd;
    private String coTel;
    private String coFax;
    private Date coLyBeg;
    private Date coLyEnd;
    private Date coCyBeg;
    private Date coCyEnd;
    private String coFax2;
    private String coTel2;
    private String coFax1;
    private String coTel1;
//	private boolean editMode;

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

    public String getCoAdd() {
        return coAdd;
    }

    public void setCoAdd(String coAdd) {
        this.coAdd = coAdd;
    }

    public String getCoChnAdd() {
        return coChnAdd;
    }

    public void setCoChnAdd(String coChnAdd) {
        this.coChnAdd = coChnAdd;
    }

    public String getCoTel() {
        return coTel;
    }

    public void setCoTel(String coTel) {
        this.coTel = coTel;
    }

    public String getCoFax() {
        return coFax;
    }

    public void setCoFax(String coFax) {
        this.coFax = coFax;
    }

    public Date getCoLyBeg() {
        return coLyBeg;
    }

    public void setCoLyBeg(Date coLyBeg) {
        this.coLyBeg = coLyBeg;
    }

    public Date getCoLyEnd() {
        return coLyEnd;
    }

    public void setCoLyEnd(Date coLyEnd) {
        this.coLyEnd = coLyEnd;
    }

    public Date getCoCyBeg() {
        return coCyBeg;
    }

    public void setCoCyBeg(Date coCyBeg) {
        this.coCyBeg = coCyBeg;
    }

    public Date getCoCyEnd() {
        return coCyEnd;
    }

    public void setCoCyEnd(Date coCyEnd) {
        this.coCyEnd = coCyEnd;
    }

    public String getCoFax2() {
        return coFax2;
    }

    public void setCoFax2(String coFax2) {
        this.coFax2 = coFax2;
    }

    public String getCoTel2() {
        return coTel2;
    }

    public void setCoTel2(String coTel2) {
        this.coTel2 = coTel2;
    }

    public String getCoFax1() {
        return coFax1;
    }

    public void setCoFax1(String coFax1) {
        this.coFax1 = coFax1;
    }

    public String getCoTel1() {
        return coTel1;
    }

    public void setCoTel1(String coTel1) {
        this.coTel1 = coTel1;
    }

//	public boolean isEditMode() {
//		return editMode;
//	}
//
//	public void setEditMode(boolean editMode) {
//		this.editMode = editMode;
//	}
}
