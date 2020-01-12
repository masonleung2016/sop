package sop.persistence.beans;

/**
 * @Author: LCF
 * @Date: 2020/1/9 10:08
 * @Package: sop.persistence.beans
 */


public class Supplier extends BaseBean {
    /**
     * table supp
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String code;
    private String name;
    //表是suChn，实际表示的值，应该是email
//	private String suChn;
    private String email;
    private String contactPerson;
    private String address;
    private String tel;
    private String fax;
    private String payTerm;
    private String credit;
    private String type;
    /*这中间的field在表中都是空null，应该是不用了。*/
    private String su_con_per1;
    private String su_con_per2;
    private String su_chn_add;
    private String su_tel1_cty;
    private String su_tel1_no;
    private String su_tel2_cty;
    private String su_tel2_no;
    private String su_fax1_cty;
    private String su_fax1_no;
    private String su_fax2_cty;
    private String su_fax2_no;
    private String su_pterm;
    private String su_ccy;
    private String su_cr_bal;
    private String su_cr_used;
    private String su_gl_inf_settle;
    /*这中间的field在表中都是空null，应该是不用了。*/
    private String websiteUrl;
    private String fscCertCode;
    private String fscValidFm;
    private String fscValidTo;
    private String bsciCertCode;
    private String bsciValidFm;
    private String bsciValidTo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPayTerm() {
        return payTerm;
    }

    public void setPayTerm(String payTerm) {
        this.payTerm = payTerm;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSu_con_per1() {
        return su_con_per1;
    }

    public void setSu_con_per1(String su_con_per1) {
        this.su_con_per1 = su_con_per1;
    }

    public String getSu_con_per2() {
        return su_con_per2;
    }

    public void setSu_con_per2(String su_con_per2) {
        this.su_con_per2 = su_con_per2;
    }

    public String getSu_chn_add() {
        return su_chn_add;
    }

    public void setSu_chn_add(String su_chn_add) {
        this.su_chn_add = su_chn_add;
    }

    public String getSu_tel1_cty() {
        return su_tel1_cty;
    }

    public void setSu_tel1_cty(String su_tel1_cty) {
        this.su_tel1_cty = su_tel1_cty;
    }

    public String getSu_tel1_no() {
        return su_tel1_no;
    }

    public void setSu_tel1_no(String su_tel1_no) {
        this.su_tel1_no = su_tel1_no;
    }

    public String getSu_tel2_cty() {
        return su_tel2_cty;
    }

    public void setSu_tel2_cty(String su_tel2_cty) {
        this.su_tel2_cty = su_tel2_cty;
    }

    public String getSu_tel2_no() {
        return su_tel2_no;
    }

    public void setSu_tel2_no(String su_tel2_no) {
        this.su_tel2_no = su_tel2_no;
    }

    public String getSu_fax1_cty() {
        return su_fax1_cty;
    }

    public void setSu_fax1_cty(String su_fax1_cty) {
        this.su_fax1_cty = su_fax1_cty;
    }

    public String getSu_fax1_no() {
        return su_fax1_no;
    }

    public void setSu_fax1_no(String su_fax1_no) {
        this.su_fax1_no = su_fax1_no;
    }

    public String getSu_fax2_cty() {
        return su_fax2_cty;
    }

    public void setSu_fax2_cty(String su_fax2_cty) {
        this.su_fax2_cty = su_fax2_cty;
    }

    public String getSu_fax2_no() {
        return su_fax2_no;
    }

    public void setSu_fax2_no(String su_fax2_no) {
        this.su_fax2_no = su_fax2_no;
    }

    public String getSu_pterm() {
        return su_pterm;
    }

    public void setSu_pterm(String su_pterm) {
        this.su_pterm = su_pterm;
    }

    public String getSu_ccy() {
        return su_ccy;
    }

    public void setSu_ccy(String su_ccy) {
        this.su_ccy = su_ccy;
    }

    public String getSu_cr_bal() {
        return su_cr_bal;
    }

    public void setSu_cr_bal(String su_cr_bal) {
        this.su_cr_bal = su_cr_bal;
    }

    public String getSu_cr_used() {
        return su_cr_used;
    }

    public void setSu_cr_used(String su_cr_used) {
        this.su_cr_used = su_cr_used;
    }

    public String getSu_gl_inf_settle() {
        return su_gl_inf_settle;
    }

    public void setSu_gl_inf_settle(String su_gl_inf_settle) {
        this.su_gl_inf_settle = su_gl_inf_settle;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getFscCertCode() {
        return fscCertCode;
    }

    public void setFscCertCode(String fscCertCode) {
        this.fscCertCode = fscCertCode;
    }

    public String getFscValidFm() {
        return fscValidFm;
    }

    public void setFscValidFm(String fscValidFm) {
        this.fscValidFm = fscValidFm;
    }

    public String getFscValidTo() {
        return fscValidTo;
    }

    public void setFscValidTo(String fscValidTo) {
        this.fscValidTo = fscValidTo;
    }

    public String getBsciCertCode() {
        return bsciCertCode;
    }

    public void setBsciCertCode(String bsciCertCode) {
        this.bsciCertCode = bsciCertCode;
    }

    public String getBsciValidFm() {
        return bsciValidFm;
    }

    public void setBsciValidFm(String bsciValidFm) {
        this.bsciValidFm = bsciValidFm;
    }

    public String getBsciValidTo() {
        return bsciValidTo;
    }

    public void setBsciValidTo(String bsciValidTo) {
        this.bsciValidTo = bsciValidTo;
    }


}
