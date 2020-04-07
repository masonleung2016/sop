package sop.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.QualityCheck;
import sop.persistence.beans.WorkflowLog;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:52
 * @Package: sop.vo
 */

public class QualityCheckDetailsVo {

    //ItemNo--JSON
    Map<String, String> qcItemCheckListMap = new HashMap<String, String>();
    //ItemNo--QCT NO
    Map<String, String> qcItemQctMap = new HashMap<String, String>();
    /**
     * qc_no
     */
    private String qcNo;
    
    private String encodeQcNo;
    
    private String qcStatus;

    private boolean isNew;
    
    private List<QualityCheckItemVo> qcItems;
    
    private String ofNo;
    
    private String poNo;
    
    private String soNo;
    
    private Date startDate;
    
    private Date dueDate;
    
    private Date actualStartDate;
    
    private Date actualCompleteDate;
    
    private String status;
    
    private String assignee;
    
    private String priority;
    
    private String category;
    
    private String watchers;
    
    private String comment;
    
    private List<WorkflowLog> logs;
    
    private String qcPoNoRef;
    
    private String qcSoNoRef;
    
    private String qcOfNoRef;
    
    public QualityCheckDetailsVo() {

    }
    public QualityCheckDetailsVo(QualityCheck qc) {
        this.qcNo = qc.getQcNo();
        this.encodeQcNo = Base64.encode(qcNo, "utf-8");
        this.qcStatus = qc.getQcStatus();
        this.actualCompleteDate = qc.getActualEndDate();
        this.actualStartDate = qc.getActualStartDate();
        this.assignee = qc.getAssignee();
        this.comment = qc.getComment();
        this.dueDate = qc.getDueDate();
        this.startDate = qc.getStartDate();
        this.qcOfNoRef = qc.getQcOfNoRef();
        this.qcPoNoRef = qc.getQcPoNoRef();
        this.qcSoNoRef = qc.getQcSoNoRef();
        this.poNo = qc.getQcPoNoRef();
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getQcStatus() {
        return qcStatus;
    }

    public void setQcStatus(String qcStatus) {
        this.qcStatus = qcStatus;
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

    public Map<String, String> getQcItemCheckListMap() {
        return qcItemCheckListMap;
    }

    public String getQcItemCheckListByItemNo(String itemNo) {
        return qcItemCheckListMap.get(itemNo);
    }

    public Map<String, String> getQcItemQctMap() {
        return qcItemQctMap;
    }

    public String getQcItemQctMapByItemNo(String itemNo) {
        return qcItemQctMap.get(itemNo);
    }

    public List<QualityCheckItemVo> getQcItems() {
        return qcItems;
    }

    public void setQcItems(List<QualityCheckItemVo> qcItems) {
        this.qcItems = qcItems;
    }

    public String getOfNo() {
        return ofNo;
    }

    public void setOfNo(String ofNo) {
        this.ofNo = ofNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
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

    public Date getActualCompleteDate() {
        return actualCompleteDate;
    }

    public void setActualCompleteDate(Date actualCompleteDate) {
        this.actualCompleteDate = actualCompleteDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWatchers() {
        return watchers;
    }

    public void setWatchers(String watchers) {
        this.watchers = watchers;
    }

    public List<WorkflowLog> getLogs() {
        return logs;
    }

    public void setLogs(List<WorkflowLog> logs) {
        this.logs = logs;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQcPoNoRef() {
        return qcPoNoRef;
    }

    public void setQcPoNoRef(String qcPoNoRef) {
        this.qcPoNoRef = qcPoNoRef;
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
}
