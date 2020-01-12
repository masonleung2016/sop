package sop.vo;

import java.util.Date;

import it.sauronsoftware.base64.Base64;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:55
 * @Package: sop.vo
 */


public class QualityCheckVo {

    /**
     * qc_po_no_ref
     */
    private String qcPoNoRef;

    private String encodePoNo;

    /**
     * qc_no
     */
    private String qcNo;

    private String encodeQcNo;

    /**
     * qc_status
     */
    private String qcStatus;

    /**
     * po_no
     */
    private String poNo;

    private Date poDate;

    private Date poLshpDate;

    private Date poEtdDate;
    private String qcSoNoRef;
    private String qcOfNoRef;
    private String assignee;
    private String comment;
    private Date startDate;
    private Date dueDate;
    private Date actualStartDate;
    private Date actualEndDate;

    public Date getPoDate() {
        return poDate;
    }

    public void setPoDate(Date poDate) {
        this.poDate = poDate;
    }

    public Date getPoLshpDate() {
        return poLshpDate;
    }

    public void setPoLshpDate(Date poLshpDate) {
        this.poLshpDate = poLshpDate;
    }

    public Date getPoEtdDate() {
        return poEtdDate;
    }

    public void setPoEtdDate(Date poEtdDate) {
        this.poEtdDate = poEtdDate;
    }

    public String getQcPoNoRef() {
        return qcPoNoRef;
    }

    public void setQcPoNoRef(String qcPoNoRef) {
        this.qcPoNoRef = qcPoNoRef;
    }

    public String getQcNo() {
        return qcNo;
    }

    public void setQcNo(String qcNo) {
        this.qcNo = qcNo;
        this.encodeQcNo = Base64.encode(qcNo, "utf-8");
    }

    public String getEncodeQcNo() {
        return encodeQcNo;
    }

    public void setEncodeQcNo(String encodeQcNo) {
        this.encodeQcNo = encodeQcNo;
    }

    public String getQcStatus() {
        return qcStatus;
    }

    public void setQcStatus(String qcStatus) {
        this.qcStatus = qcStatus;
    }

    public String getEncodePoNo() {
        return encodePoNo;
    }

    public void setEncodePoNo(String encodePoNo) {
        this.encodePoNo = encodePoNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
        this.encodePoNo = Base64.encode(poNo, "utf-8");
    }

    public String getQcSoNoRef() {
        return qcSoNoRef;
    }

    public void setQcSoNoRef(String qcSoNoRef) {
        this.qcSoNoRef = qcSoNoRef;
    }

    public String getQcOfNoRef() {
        return qcOfNoRef;
    }

    public void setQcOfNoRef(String qcOfNoRef) {
        this.qcOfNoRef = qcOfNoRef;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

}
