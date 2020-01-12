package sop.vo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:40
 * @Package: sop.vo
 */

public class SupplierVo {
    /**
     * table supp
     */
    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    //表是suChn，实际表示的值，应该是email
    //private String suChn;
    private String email;
    private String contactPerson;
    private String address;
    private String tel;
    private String fax;
    private String payTerm;
    private String credit;
    private String type;
    private String websiteUrl;
    private String fscCertCode;
    private String fscValidFm;
    private String fscValidTo;
    private String bsciCertCode;
    private String bsciValidFm;
    private String bsciValidTo;

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
